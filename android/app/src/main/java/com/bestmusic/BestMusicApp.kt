package com.bestmusic

import android.app.Application
import com.bestmusic.data.local.PlaylistRepository
import com.bestmusic.player.PlayerManager

class BestMusicApp : Application() {
    val repository by lazy { MusicRepository(this) }
    val playlistRepository by lazy { PlaylistRepository(this) }
    val playerManager by lazy { PlayerManager(this) }
}