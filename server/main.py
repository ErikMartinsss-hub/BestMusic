"""
BestMusic server — resolve e pesquisa YouTube via yt-dlp.

Executar (na pasta server/):

    pip install -r requirements.txt
    python main.py

O app Android espera o servidor em http://10.0.2.2:8000 (emulador).
Em um aparelho físico, configure o IP/porta do servidor no app
(menu Configurações) e rode uvicorn em 0.0.0.0.
"""

import uvicorn
from fastapi import FastAPI, Query, HTTPException
from fastapi.middleware.cors import CORSMiddleware

import yt_dlp

app = FastAPI(title="BestMusic")

app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_methods=["*"],
    allow_headers=["*"],
)

# Preferir áudio compacto mp4 (m4a). O ExoPlayer/Media3 toca m4a e opus nativamente.
YDL_OPTS = {
    "quiet": True,
    "no_warnings": True,
    "noplaylist": True,
    "format": "bestaudio[ext=m4a]/bestaudio/best",
    "extract_flat": False,
    "socket_timeout": 20,
}


def _thumb(info: dict) -> str | None:
    t = info.get("thumbnail")
    if t:
        return t
    for f in info.get("thumbnails", []) or []:
        if f.get("url"):
            return f["url"]
    return None


@app.get("/api/search")
def search(q: str = Query(..., min_length=1)) -> list[dict]:
    """Pesquisa músicas. Retorna apenas metadados (rápido)."""
    try:
        with yt_dlp.YoutubeDL({**YDL_OPTS, "extract_flat": True}) as ydl:
            entries = ydl.extract_info(f"ytsearch5:{q}", download=False).get("entries", [])
    except Exception as e:  # noqa: BLE001
        raise HTTPException(status_code=502, detail=f"yt-dlp falhou: {e}")

    out = []
    for e in entries:
        out.append(
            {
                "id": e.get("id"),
                "title": e.get("title"),
                "artist": None,
                "thumbnail": _thumb(e) or f"https://i.ytimg.com/vi/{e.get('id')}/hqdefault.jpg",
                "durationMs": int((e.get("duration") or 0) * 1000),
            }
        )
    return [x for x in out if x["id"] and x["title"]]


@app.get("/api/stream/{video_id}")
def stream(video_id: str) -> dict:
    """Resolve a URL direta de áudio de um vídeo (chamado ao tocar na faixa)."""
    try:
        with yt_dlp.YoutubeDL(YDL_OPTS) as ydl:
            info = ydl.extract_info(video_id, download=False)
    except Exception as e:  # noqa: BLE001
        raise HTTPException(status_code=502, detail=f"Não foi possível resolver o stream: {e}")

    url = (info.get("url") or "").strip()
    if not url:
        raise HTTPException(status_code=502, detail="Stream de áudio não encontrado")

    return {
        "id": info.get("id"),
        "title": info.get("title"),
        "artist": info.get("uploader"),
        "thumbnail": _thumb(info) or f"https://i.ytimg.com/vi/{video_id}/hqdefault.jpg",
        "durationMs": int((info.get("duration") or 0) * 1000),
        "url": url,
    }


if __name__ == "__main__":
    uvicorn.run("main:app", host="0.0.0.0", port=8000)