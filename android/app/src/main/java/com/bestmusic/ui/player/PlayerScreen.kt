package com.bestmusic.ui.player

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.media3.common.Player
import coil.compose.AsyncImage
import com.bestmusic.player.PlaybackUiState
import com.bestmusic.player.PlayerManager
import com.bestmusic.util.formatDuration

@Composable
fun PlayerScreen(
    state: PlaybackUiState,
    player: PlayerManager,
    onBack: () -> Unit,
) {
    val track = state.track

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        IconButton(onClick = onBack) {
            Icon(Icons.Filled.ArrowBack, contentDescription = "Voltar")
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            AsyncImage(
                model = track?.thumbnail,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(20.dp)),
                contentScale = ContentScale.Crop,
            )

            Spacer(Modifier.height(24.dp))

            Text(
                track?.title ?: "Nada tocando",
                style = MaterialTheme.typography.titleLarge,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
            )
            Text(
                track?.artist ?: "",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )

            Spacer(Modifier.height(16.dp))

            SeekBar(state, onSeek = player::seekTo)

            Spacer(Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = player::toggleShuffle) {
                    Icon(
                        Icons.Filled.Shuffle,
                        contentDescription = "Aleatório",
                        tint = if (state.shuffleEnabled) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
                IconButton(
                    onClick = player::previous,
                    enabled = state.hasPrevious,
                ) {
                    Icon(Icons.Filled.SkipPrevious, contentDescription = "Anterior", Modifier.size(40.dp))
                }
                IconButton(
                    onClick = player::toggle,
                    modifier = Modifier
                        .size(76.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary),
                ) {
                    Icon(
                        if (state.isPlaying) Icons.Filled.Pause else Icons.Filled.PlayArrow,
                        contentDescription = if (state.isPlaying) "Pausar" else "Tocar",
                        modifier = Modifier.size(44.dp),
                        tint = MaterialTheme.colorScheme.onPrimary,
                    )
                }
                IconButton(
                    onClick = player::next,
                    enabled = state.hasNext,
                ) {
                    Icon(Icons.Filled.SkipNext, contentDescription = "Próxima", Modifier.size(40.dp))
                }
                IconButton(onClick = player::cycleRepeat) {
                    Icon(
                        Icons.Filled.Repeat,
                        contentDescription = "Repetir",
                        tint = if (state.repeatMode != Player.REPEAT_MODE_OFF) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
        }
    }
}

@Composable
private fun SeekBar(state: PlaybackUiState, onSeek: (Long) -> Unit) {
    val max = if (state.durationMs > 0) state.durationMs.toFloat() else 1f
    var sliderPos by remember(state.track?.id) { mutableStateOf(state.positionMs.toFloat()) }

    LaunchedEffect(state.positionMs) {
        sliderPos = state.positionMs.coerceAtMost(max.toLong()).toFloat()
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Slider(
            value = sliderPos.coerceIn(0f, max),
            onValueChange = { sliderPos = it },
            onValueChangeFinished = { onSeek(sliderPos.toLong()) },
            modifier = Modifier.fillMaxWidth(),
            enabled = state.durationMs > 0,
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(formatDuration(sliderPos.toLong()), style = MaterialTheme.typography.labelSmall)
            Text(formatDuration(max.toLong()), style = MaterialTheme.typography.labelSmall)
        }
    }
}

@Composable
fun MiniPlayerBar(
    state: PlaybackUiState,
    onClick: () -> Unit,
    onToggle: () -> Unit,
) {
    val track = state.track
    Surface(
        tonalElevation = 6.dp,
        shadowElevation = 10.dp,
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().clickable(onClick = onClick).padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AsyncImage(
                model = track?.thumbnail,
                contentDescription = null,
                modifier = Modifier.size(44.dp).clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop,
            )
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text(
                    track?.title ?: "",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium,
                )
                Text(
                    track?.artist ?: "",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
            IconButton(onClick = onToggle) {
                Icon(
                    if (state.isPlaying) Icons.Filled.Pause else Icons.Filled.PlayArrow,
                    contentDescription = if (state.isPlaying) "Pausar" else "Tocar",
                )
            }
        }
    }
}