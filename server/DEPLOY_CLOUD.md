# BestMusic Server - Deploy na Nuvem (GRÁTIS)

## Opção 1: Railway (Recomendado - 500h/mês grátis)

1. Crie conta em: https://railway.app
2. "New Project" → "Deploy from GitHub repo"
3. Conecte seu GitHub e selecione este repo
4. Railway detecta `package.json` e `railway.toml` automaticamente
5. Deploy automático! URL será algo como: `https://bestmusic-server.up.railway.app`

## Opção 2: Render (Grátis - 750h/mês)

1. Crie conta em: https://render.com
2. "New Web Service" → Conecte GitHub
3. Build Command: (vazio)
4. Start Command: `node server.mjs`
5. URL será: `https://bestmusic-server.onrender.com`

## Opção 3: Fly.io (Grátis - 3 VMs pequenas)

```bash
flyctl launch --no-deploy
flyctl deploy
```

## Configurar no App Android

No app, vá em Configurações (⚙) e use a URL da nuvem:
- Railway: `https://bestmusic-server.up.railway.app`
- Render: `https://bestmusic-server.onrender.com`

**Vantagem:** Funciona em QUALQUER celular na rua, não precisa do PC ligado!

## Variáveis de Ambiente (Opcional)
- `PORT`: 8000 (padrão)
- `YTDLP_PATH`: caminho do yt-dlp (auto-detecta)