// BestMusic server (Node, sem dependências).
//
// Como rodar:
//   node server.mjs
//
// Emulador Android: http://10.0.2.2:8000
// Aparelho físico:  http://<IP-da-sua-maquina>:8000 (configurar no app, menu ⚙)
// Render/Cloud: baixa yt-dlp Linux automaticamente no startup.

import { spawn } from "node:child_process";
import { createServer } from "node:http";
import { join, dirname } from "node:path";
import { fileURLToPath } from "node:url";
import { existsSync, chmodSync, createWriteStream, unlink } from "node:fs";
import { platform } from "node:process";
import https from "node:https";

const __dirname = dirname(fileURLToPath(import.meta.url));

// Resolve yt-dlp binary per platform
async function getYtDlpPath() {
  const isWindows = platform === "win32";
  const binaryName = isWindows ? "yt-dlp.exe" : "yt-dlp";
  const localPath = join(__dirname, binaryName);

  if (existsSync(localPath)) {
    if (!isWindows) chmodSync(localPath, 0o755);
    return localPath;
  }

  // Download Linux binary on non-Windows (Render, etc.)
  if (!isWindows) {
    console.log("Baixando yt-dlp Linux...");
    const url = "https://github.com/yt-dlp/yt-dlp/releases/latest/download/yt-dlp_linux";
    await downloadFile(url, localPath);
    chmodSync(localPath, 0o755);
    console.log("yt-dlp pronto:", localPath);
    return localPath;
  }

  throw new Error("yt-dlp não encontrado. No Windows, coloque yt-dlp.exe na pasta server/");
}

function downloadFile(url, dest, redirectCount = 0) {
  return new Promise((resolve, reject) => {
    if (redirectCount > 10) {
      reject(new Error("Too many redirects"));
      return;
    }
    const file = createWriteStream(dest);
    https.get(url, (response) => {
      if (response.statusCode >= 300 && response.statusCode < 400 && response.headers.location) {
        // Follow redirect
        file.close();
        unlink(dest, () => {});
        downloadFile(response.headers.location, dest, redirectCount + 1).then(resolve).catch(reject);
        return;
      }
      if (response.statusCode !== 200) {
        reject(new Error(`Failed to download: ${response.statusCode}`));
        return;
      }
      response.pipe(file);
      file.on("finish", () => file.close(resolve));
    }).on("error", (err) => {
      unlink(dest, () => {});
      reject(err);
    });
  });
}

// Initialize YTDLP path
let YTDLP_PATH = null;
let YTDLP_READY = getYtDlpPath().then(p => { YTDLP_PATH = p; }).catch(e => console.error("yt-dlp init error:", e));

async function run(args, timeoutMs = 60000) {
  await YTDLP_READY;
  return new Promise((resolve, reject) => {
    const child = spawn(YTDLP_PATH, args, { windowsHide: true });
    let stdout = "";
    let stderr = "";
    child.stdout.setEncoding("utf8");
    child.stderr.setEncoding("utf8");
    child.stdout.on("data", (d) => (stdout += d));
    child.stderr.on("data", (d) => (stderr += d));
    const timer = setTimeout(() => {
      child.kill();
      reject(new Error(`yt-dlp timeout após ${timeoutMs}ms`));
    }, timeoutMs);
    child.on("error", (err) => {
      clearTimeout(timer);
      reject(new Error(`não foi possível executar yt-dlp: ${err.message}`));
    });
    child.on("close", (code) => {
      clearTimeout(timer);
      if (code === 0) resolve(stdout.trim());
      else reject(new Error(stderr.trim().split("\n").pop() || `yt-dlp saiu com código ${code}`));
    });
  });
}

function parseJson(text) {
  const start = text.indexOf("{");
  if (start < 0) throw new Error("nenhum JSON retornado pelo yt-dlp");
  return JSON.parse(text.slice(start));
}

const searchArgs = (q) => [
  "--flat-playlist",
  "--no-warnings",
  "--quiet",
  "--dump-single-json",
  "--playlist-items", "1-6",
  `ytsearch6:${q}`,
];

const streamArgs = (id) => [
  "--no-warnings",
  "--quiet",
  "--dump-single-json",
  "-f", "bestaudio[ext=m4a]/bestaudio/best",
  id,
];

const thumb = (info) =>
  info.thumbnail ||
  (info.thumbnails?.length ? info.thumbnails.at(-1).url : null) ||
  `https://i.ytimg.com/vi/${info.id}/hqdefault.jpg`;

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
      const data = parseJson(await run(searchArgs(q), 40000));
      const entries = (data.entries || []).filter((e) => e.id && e.title);
      return send(
        res,
        200,
        entries.map((e) => ({
          id: e.id,
          title: e.title,
          artist: null,
          durationMs: (e.duration || 0) * 1000,
          thumbnail: e.thumbnail || `https://i.ytimg.com/vi/${e.id}/hqdefault.jpg`,
        })),
      );
    }

    const m = url.pathname.match(/^\/api\/stream\/([^/]+)$/);
    if (m) {
      const id = decodeURIComponent(m[1]);
      const info = parseJson(await run(streamArgs(id)));
      if (!info.url) return send(res, 502, { detail: "Stream de áudio não encontrado" });
      return send(res, 200, {
        id: info.id,
        title: info.title,
        artist: info.uploader || null,
        durationMs: (info.duration || 0) * 1000,
        thumbnail: thumb(info),
        url: info.url,
      });
    }

    send(res, 404, { detail: "Endpoint não encontrado" });
  } catch (err) {
    send(res, 502, { detail: `yt-dlp falhou: ${err.message}` });
  }
});

const PORT = Number(process.env.PORT || 8000);
server.listen(PORT, "0.0.0.0", () => {
  console.log(`BestMusic server rodando em http://0.0.0.0:${PORT}`);
  console.log(`App Android (emulador): http://10.0.2.2:${PORT}`);
});