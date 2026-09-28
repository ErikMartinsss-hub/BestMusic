// BestMusic server - usa Invidious API + Piped API fallback.
// Melhor estabilidade com múltiplas instâncias e retry.

import { createServer } from "node:http";
import https from "node:https";

// Invidious instances (mais estáveis primeiro)
const INVIDIOUS_INSTANCES = [
  "https://yewtu.be",
  "https://invidious.snopyta.org",
  "https://invidious.nerdvpn.de",
  "https://invidious.kavin.rocks",
  "https://yewtu.be",
];

// Piped instances (alternativa)
const PIPED_INSTANCES = [
  "https://piped.video",
  "https://piped.kavin.rocks",
  "https://piped.mha.fi",
];

let invidiousIndex = 0;
let pipedIndex = 0;

function getInvidious() { return INVIDIOUS_INSTANCES[invidiousIndex % INVIDIOUS_INSTANCES.length]; }
function nextInvidious() { invidiousIndex++; }

function getPiped() { return PIPED_INSTANCES[pipedIndex % PIPED_INSTANCES.length]; }
function nextPiped() { pipedIndex++; }

function httpsGet(url, timeoutMs = 10000) {
  return new Promise((resolve, reject) => {
    const req = https.get(url, (res) => {
      let data = "";
      res.on("data", (chunk) => (data += chunk));
      res.on("end", () => {
        const contentType = res.headers["content-type"] || "";
        if (res.statusCode >= 400) {
          reject(new Error(`HTTP ${res.statusCode}: ${data.substring(0, 200)}`));
        } else if (!contentType.includes("application/json")) {
          reject(new Error(`Non-JSON (${contentType}): ${data.substring(0, 200)}`));
        } else {
          resolve(data);
        }
      });
    });
    req.on("error", reject);
    req.setTimeout(timeoutMs, () => req.destroy(new Error("Timeout")));
  });
}

// Try multiple instances with exponential backoff
async function tryInstances(instances, getNext, fn, maxRetries = 3) {
  let lastError;
  for (let attempt = 0; attempt < maxRetries; attempt++) {
    for (let i = 0; i < instances.length; i++) {
      try {
        return await fn(instances[i]);
      } catch (err) {
        console.warn(`Instance failed: ${err.message}`);
      }
    }
    // Wait before retry
    await new Promise(r => setTimeout(r, 1000 * (attempt + 1)));
  }
  throw new Error("All instances failed after retries");
}

async function searchInvidious(query) {
  return tryInstances(INVIDIOUS_INSTANCES, () => nextInvidious(), async (instance) => {
    const url = `${instance}/api/v1/search?q=${encodeURIComponent(query)}&type=video&page=1`;
    const data = await httpsGet(url);
    const results = JSON.parse(data);
    return results.map((v) => ({
      id: v.videoId,
      title: v.title,
      artist: v.author,
      durationMs: v.lengthSeconds * 1000,
      thumbnail: v.videoThumbnails?.[v.videoThumbnails.length - 1]?.url || `https://i.ytimg.com/vi/${v.videoId}/hqdefault.jpg`,
    })).filter((v) => v.id && v.title);
  }, 2);
}

async function searchPiped(query) {
  return tryInstances(PIPED_INSTANCES, () => nextPiped(), async (instance) => {
    const url = `${instance}/api/v1/search?q=${encodeURIComponent(query)}&filter=music`;
    const data = await httpsGet(url);
    const results = JSON.parse(data);
    return results.items?.map((v) => ({
      id: v.id,
      title: v.title,
      artist: v.uploader,
      durationMs: v.duration * 1000,
      thumbnail: v.thumbnails?.[v.thumbnails.length - 1]?.url || `https://i.ytimg.com/vi/${v.id}/hqdefault.jpg`,
    })).filter((v) => v.id && v.title) || [];
  }, 2);
}

async function getStreamInvidious(videoId) {
  return tryInstances(INVIDIOUS_INSTANCES, () => nextInvidious(), async (instance) => {
    const url = `${instance}/api/v1/videos/${videoId}?fields=videoId,title,author,lengthSeconds,videoThumbnails,formatStreams,adaptiveFormats`;
    const data = await httpsGet(url);
    const info = JSON.parse(data);
    
    const audioFormat = [...(info.adaptiveFormats || []), ...(info.formatStreams || [])]
      .filter((f) => f.type?.includes("audio") || f.mimeType?.includes("audio"))
      .sort((a, b) => (b.bitrate || 0) - (a.bitrate || 0))[0];
    
    if (!audioFormat?.url) throw new Error("No audio stream");
    
    return {
      id: info.videoId,
      title: info.title,
      artist: info.author,
      durationMs: info.lengthSeconds * 1000,
      thumbnail: info.videoThumbnails?.[info.videoThumbnails.length - 1]?.url || `https://i.ytimg.com/vi/${videoId}/hqdefault.jpg`,
      url: audioFormat.url,
    };
  }, 3);
}

async function getStreamPiped(videoId) {
  return tryInstances(PIPED_INSTANCES, () => nextPiped(), async (instance) => {
    const url = `${instance}/api/v1/streams/${videoId}`;
    const data = await httpsGet(url);
    const info = JSON.parse(data);
    
    // Piped returns audioStreams array
    const audioStream = info.audioStreams?.sort((a, b) => (b.bitrate || 0) - (a.bitrate || 0))[0];
    if (!audioStream?.url) throw new Error("No audio stream");
    
    return {
      id: info.id,
      title: info.title,
      artist: info.uploader,
      durationMs: info.duration * 1000,
      thumbnail: info.thumbnails?.[info.thumbnails.length - 1]?.url || `https://i.ytimg.com/vi/${videoId}/hqdefault.jpg`,
      url: audioStream.url,
    };
  }, 3);
}

// Main search - try Invidious first, fallback to Piped
async function search(query) {
  try {
    return await searchInvidious(query);
  } catch (e) {
    console.warn("Invidious search failed, trying Piped:", e.message);
    return await searchPiped(query);
  }
}

// Main stream - try Invidious first, fallback to Piped
async function getStream(videoId) {
  try {
    return await getStreamInvidious(videoId);
  } catch (e) {
    console.warn("Invidious stream failed, trying Piped:", e.message);
    return await getStreamPiped(videoId);
  }
}

function httpsGet(url, timeoutMs = 10000) {
  return new Promise((resolve, reject) => {
    const req = https.get(url, (res) => {
      let data = "";
      res.on("data", (chunk) => (data += chunk));
      res.on("end", () => {
        const contentType = res.headers["content-type"] || "";
        if (res.statusCode >= 400) {
          reject(new Error(`HTTP ${res.statusCode}: ${data.substring(0, 200)}`));
        } else if (!contentType.includes("application/json")) {
          reject(new Error(`Non-JSON (${contentType}): ${data.substring(0, 200)}`));
        } else {
          resolve(data);
        }
      });
    });
    req.on("error", reject);
    req.setTimeout(10000, () => req.destroy(new Error("Timeout")));
  });
}

function send(res, code, payload) {
  const body = typeof payload === "string" ? payload : JSON.stringify(payload);
  res.writeHead(code, {
    "Content-Type": "application/json; charset=utf-8",
    "Access-Control-Allow-Origin": "*",
  });
  res.end(body);
}

const server = createServer(async (req, res) => {
  try {
    const url = new URL(req.url, "http://localhost");

    if (url.pathname === "/api/search") {
      const q = (url.searchParams.get("q") || "").trim();
      if (!q) return send(res, 400, { detail: "Parâmetro q é obrigatório" });
      
      const results = await search(q);
      return send(res, 200, results.slice(0, 6));
    }

    const m = url.pathname.match(/^\/api\/stream\/([^/]+)$/);
    if (m) {
      const id = decodeURIComponent(m[1]);
      const info = await getStream(id);
      return send(res, 200, info);
    }

    send(res, 404, { detail: "Endpoint não encontrado" });
  } catch (err) {
    console.error("Server error:", err.message);
    send(res, 502, { detail: err.message });
  }
});

const PORT = Number(process.env.PORT || 8000);
server.listen(PORT, "0.0.0.0", () => {
  console.log(`BestMusic server rodando em http://0.0.0.0:${PORT}`);
  console.log(`App Android (emulador): http://10.0.2.2:${PORT}`);
});