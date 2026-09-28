// BestMusic server - usa Invidious API (proxy YouTube sem bloqueio).
//
// Como rodar:
//   node server.mjs
//
// Invidious instances públicas:
//   https://yewtu.be (recomendado)
//   https://piped.video
//   https://invidious.snopyta.org

import { createServer } from "node:http";
import https from "node:https";

const INVIDIOUS_INSTANCES = [
  "https://yewtu.be",
  "https://invidious.snopyta.org",
  "https://yewtu.be",
];

let currentInstanceIndex = 0;

function getInstance() {
  return INVIDIOUS_INSTANCES[currentInstanceIndex];
}

function nextInstance() {
  currentInstanceIndex = (currentInstanceIndex + 1) % INVIDIOUS_INSTANCES.length;
  return getInstance();
}

function httpsGet(url) {
  return new Promise((resolve, reject) => {
    https.get(url, (res) => {
      let data = "";
      res.on("data", (chunk) => (data += chunk));
      res.on("end", () => {
        if (res.statusCode >= 400) {
          reject(new Error(`HTTP ${res.statusCode}: ${data}`));
        } else {
          resolve(data);
        }
      });
    }).on("error", reject);
  });
}

async function searchInvidious(query) {
  const instance = getInstance();
  const url = `${instance}/api/v1/search?q=${encodeURIComponent(query)}&type=video&page=1`;
  
  try {
    const data = await httpsGet(url);
    const results = JSON.parse(data);
    return results.map((v) => ({
      id: v.videoId,
      title: v.title,
      artist: v.author,
      durationMs: v.lengthSeconds * 1000,
      thumbnail: v.videoThumbnails?.[v.videoThumbnails.length - 1]?.url || `https://i.ytimg.com/vi/${v.videoId}/hqdefault.jpg`,
    })).filter((v) => v.id && v.title);
  } catch (err) {
    nextInstance();
    throw new Error(`Invidious search failed: ${err.message}`);
  }
}

async function getStreamInfo(videoId) {
  const instance = getInstance();
  const url = `${instance}/api/v1/videos/${videoId}?fields=videoId,title,author,lengthSeconds,videoThumbnails,formatStreams,adaptiveFormats`;
  
  try {
    const data = await httpsGet(url);
    const info = JSON.parse(data);
    
    // Get best audio stream
    const audioFormat = [...(info.adaptiveFormats || []), ...(info.formatStreams || [])]
      .filter((f) => f.type?.includes("audio") || f.mimeType?.includes("audio"))
      .sort((a, b) => (b.bitrate || 0) - (a.bitrate || 0))[0];
    
    if (!audioFormat?.url) {
      throw new Error("No audio stream found");
    }
    
    return {
      id: info.videoId,
      title: info.title,
      artist: info.author,
      durationMs: info.lengthSeconds * 1000,
      thumbnail: info.videoThumbnails?.[info.videoThumbnails.length - 1]?.url || `https://i.ytimg.com/vi/${videoId}/hqdefault.jpg`,
      url: audioFormat.url,
    };
  } catch (err) {
    nextInstance();
    throw new Error(`Invidious stream failed: ${err.message}`);
  }
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
      
      const results = await searchInvidious(q);
      return send(res, 200, results.slice(0, 6));
    }

    const m = url.pathname.match(/^\/api\/stream\/([^/]+)$/);
    if (m) {
      const id = decodeURIComponent(m[1]);
      const info = await getStreamInfo(id);
      return send(res, 200, info);
    }

    send(res, 404, { detail: "Endpoint não encontrado" });
  } catch (err) {
    send(res, 502, { detail: err.message });
  }
});

const PORT = Number(process.env.PORT || 8000);
server.listen(PORT, "0.0.0.0", () => {
  console.log(`BestMusic server rodando em http://0.0.0.0:${PORT}`);
  console.log(`App Android (emulador): http://10.0.2.2:${PORT}`);
});