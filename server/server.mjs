// BestMusic server - Piped API (mais estável que Invidious no Render)

import { createServer } from "node:http";
import https from "node:https";

const PIPED_INSTANCES = [
  "https://piped.video",
  "https://piped.kavin.rocks",
  "https://piped.mha.fi",
  "https://piped.tokhmi.xyz",
];

let pipedIndex = 0;
function getPiped() { return PIPED_INSTANCES[pipedIndex % PIPED_INSTANCES.length]; }
function nextPiped() { pipedIndex++; }

function httpsGet(url) {
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
    req.setTimeout(15000, () => req.destroy(new Error("Timeout")));
  });
}

// Try all instances with retries
async function tryAllInstances(fn, maxRetries = 2) {
  let lastError;
  for (let attempt = 0; attempt < maxRetries; attempt++) {
    for (let i = 0; i < PIPED_INSTANCES.length; i++) {
      try {
        return await fn(PIPED_INSTANCES[i]);
      } catch (err) {
        console.warn(`Instance failed: ${err.message}`);
      }
    }
    await new Promise(r => setTimeout(r, 2000 * (attempt + 1)));
  }
  throw lastError || new Error("All Piped instances failed");
}

async function search(query) {
  return tryAllInstances(async (instance) => {
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

async function getStream(videoId) {
  return tryAllInstances(async (instance) => {
    const url = `${instance}/api/v1/streams/${videoId}`;
    const data = await httpsGet(url);
    const info = JSON.parse(data);
    
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