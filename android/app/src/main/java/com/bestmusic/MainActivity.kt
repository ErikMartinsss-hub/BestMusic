package com.bestmusic

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bestmusic.player.PlaybackUiState
import com.bestmusic.ui.MainViewModel
import com.bestmusic.ui.player.MiniPlayerBar
import com.bestmusic.ui.player.PlayerScreen
import com.bestmusic.ui.playlist.PlaylistScreen
import com.bestmusic.ui.search.SearchScreen
import com.bestmusic.ui.theme.BestMusicTheme

class MainActivity : ComponentActivity() {

    private val notificationPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestNotificationPermissionIfNeeded()
        setContent {
            BestMusicTheme {
                BestMusicRoot()
            }
        }
    }

    private fun requestNotificationPermissionIfNeeded() {
        if (Build.VERSION.SDK_INT >= 33 &&
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS,
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            notificationPermission.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }
}

private enum class Screen { Search, Player, Playlists }

@Composable
fun BestMusicRoot(
    viewModel: MainViewModel = viewModel(),
    playlistViewModel: com.bestmusic.ui.PlaylistViewModel = viewModel(),
) {
    val playerState by viewModel.player.state.collectAsState()
    var screen by rememberSaveable { mutableStateOf(Screen.Search) }

    Scaffold(
        bottomBar = {
            if (playerState.track != null && screen != Screen.Player) {
                MiniPlayerBar(
                    state = playerState,
                    onClick = { screen = Screen.Player },
                    onToggle = viewModel.player::toggle,
                )
            }
        },
    ) { padding ->
        Box(Modifier.fillMaxSize().padding(padding)) {
            when (screen) {
                Screen.Search -> SearchScreen(
                    viewModel = viewModel,
                    playlistViewModel = playlistViewModel,
                    onOpenPlayer = { screen = Screen.Player },
                    onOpenPlaylists = { screen = Screen.Playlists },
                )
                Screen.Player -> PlayerScreen(
                    state = playerState,
                    player = viewModel.player,
                    onBack = { screen = Screen.Search },
                )
                Screen.Playlists -> PlaylistScreen(
                    viewModel = playlistViewModel,
                    onBack = { screen = Screen.Search },
                    onPlayTrack = { track ->
                        viewModel.play(com.bestmusic.data.model.Track(
                            id = track.trackId,
                            title = track.title,
                            artist = track.artist,
                            thumbnail = track.thumbnail,
                            durationMs = track.durationMs
                        ))
                        screen = Screen.Player
                    },
                )
            }
        }
    }
}