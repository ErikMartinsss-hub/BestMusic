// BestMusic server - Invidious API (yewtu.be) - mais confiável no Render

import { createServer } from "node:http";
import https from "node:https";

const INVIDIOUS_INSTANCES = [
  "https://yewtu.be",
  "https://invidious.snopyta.org",
  "https://invidious.nerdvpn.de",
  "https://invidious.kavin.rocks",
  "https://yewtu.be",
];

let instanceIndex = 0;

function getInstance() {
  return INVIDIOUS_INSTANCES[instanceIndex % INVIDIOUS_INSTANCES.length];
}

function nextInstance() {
  instanceIndex++;
  return INVIDIOUS_INSTANCES[instanceIndex % INVIDIOUS_INSTANCES.length];
}

function httpsGet(url) {
  return new Promise((resolve, reject) => {
    const req = https.request(url, {
      method: "GET",
      rejectUnauthorized: false,
      timeout: 15000,
      headers: {
        "User-Agent": "Mozilla/5.0 (Linux; Android 13; Pixel 7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/116.0.0.0 Mobile Safari/537.36",
        "Accept": "application/json",
      },
    }, (res) => {
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
    req.on("timeout", () => req.destroy(new Error("Timeout")));
    req.end();
  });
}

// Try all instances with retries
async function tryInstances(fn, maxRetries = 2) {
  let lastError;
  for (let attempt = 0; attempt < maxRetries; attempt++) {
    for (let i = 0; i < 5; i++) {
      const instance = INVIDIOUS_INSTANCES[instanceIndex % INVIDIOUS_INSTANCES.length];
      instanceIndex++;
      try {
        return await fn(instance);
      } catch (err) {
        console.warn(`Instance ${instance} failed: ${err.message}`);
      }
    }
    await new Promise(r => setTimeout(r, 2000 * (attempt + 1)));
  }
  throw lastError || new Error("All Invidious instances failed");
}

async function search(query) {
  return tryInstances(async (instance) => {
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

async function getStream(videoId) {
  return tryInstances(async (instance) => {
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

function httpsGet(url) {
  return new Promise((resolve, reject) => {
    const req = https.request(url, {
      method: "GET",
      rejectUnauthorized: false,
      timeout: 15000,
      headers: {
        "User-Agent": "Mozilla/5.0 (Linux; Android 13; Pixel 7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/116.0.0.0 Mobile Safari/537.36",
        "Accept": "application/json",
      },
    }, (res) => {
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
    req.on("timeout", () => req.destroy(new Error("Timeout")));
    req.end();
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