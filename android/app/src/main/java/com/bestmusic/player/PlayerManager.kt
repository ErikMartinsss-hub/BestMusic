package com.bestmusic.player

import android.content.ComponentName
import android.content.Context
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.bestmusic.data.model.Track
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class PlaybackUiState(
    val track: Track? = null,
    val isPlaying: Boolean = false,
    val positionMs: Long = 0,
    val durationMs: Long = 0,
    val hasPrevious: Boolean = false,
    val hasNext: Boolean = false,
    val shuffleEnabled: Boolean = false,
    val repeatMode: Int = Player.REPEAT_MODE_OFF,
)

class PlayerManager(context: Context) : Player.Listener {

    private val appContext = context.applicationContext
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    private var controller: MediaController? = null
    private var ticker: Job? = null

    private val _state = MutableStateFlow(PlaybackUiState())
    val state: StateFlow<PlaybackUiState> = _state.asStateFlow()

    init {
        connect()
    }

    private fun connect() {
        val sessionToken = SessionToken(appContext, ComponentName(appContext, PlaybackService::class.java))
        val listener = object : MediaController.Listener {
            override fun onDisconnected(controller: MediaController) {
                if (this@PlayerManager.controller === controller) {
                    controller.removeListener(this@PlayerManager)
                    this@PlayerManager.controller = null
                    stopTicker()
                    publish()
                }
            }
        }
        val future = MediaController.Builder(appContext, sessionToken)
            .setListener(listener)
            .buildAsync()
        future.addListener({
            val c = runCatching { future.get() }.getOrNull() ?: return@addListener
            this@PlayerManager.controller = c
            c.addListener(this@PlayerManager)
            startTicker()
            publish()
        }, ContextCompat.getMainExecutor(appContext))
    }

    fun play(track: Track, streamUrl: String) {
        val c = controller ?: return
        c.setMediaItem(buildMediaItem(track, streamUrl))
        c.prepare()
        c.play()
    }

    fun toggle() {
        val c = controller ?: return
        if (c.isPlaying) c.pause() else c.play()
    }

    fun next() = controller?.seekToNextMediaItem()

    fun previous() = controller?.seekToPreviousMediaItem()

    fun seekTo(positionMs: Long) = controller?.seekTo(positionMs)

    fun toggleShuffle() {
        controller?.let { it.shuffleModeEnabled = !it.shuffleModeEnabled }
    }

    fun cycleRepeat() {
        controller?.let { c ->
            c.repeatMode = when (c.repeatMode) {
                Player.REPEAT_MODE_OFF -> Player.REPEAT_MODE_ALL
                Player.REPEAT_MODE_ALL -> Player.REPEAT_MODE_ONE
                else -> Player.REPEAT_MODE_OFF
            }
        }
    }

    override fun onEvents(player: Player, events: Player.Events) {
        if (events.containsAny(
                Player.EVENT_PLAYBACK_STATE_CHANGED,
                Player.EVENT_IS_PLAYING_CHANGED,
                Player.EVENT_MEDIA_ITEM_TRANSITION,
                Player.EVENT_MEDIA_METADATA_CHANGED,
                Player.EVENT_PLAYER_ERROR,
            )
        ) publish()
    }

    private fun startTicker() {
        stopTicker()
        ticker = scope.launch {
            while (true) {
                publish()
                delay(500)
            }
        }
    }

    private fun stopTicker() {
        ticker?.cancel()
        ticker = null
    }

    private fun publish() {
        val c = controller ?: return
        val item = c.currentMediaItem
        val md = item?.mediaMetadata
        val duration = c.duration.takeIf { it != C.TIME_UNSET && it > 0 }
        val track = if (md?.title != null && item.mediaId.isNotBlank()) {
            Track(
                id = item.mediaId,
                title = md.title.toString(),
                artist = md.artist?.toString(),
                thumbnail = md.artworkUri?.toString(),
                durationMs = duration ?: md.durationMs.takeIf { it != null && it != C.TIME_UNSET && it > 0 } ?: 0,
            )
        } else null
        _state.value = PlaybackUiState(
            track = track,
            isPlaying = c.isPlaying,
            positionMs = c.currentPosition.coerceAtLeast(0),
            durationMs = duration ?: (track?.durationMs ?: 0),
            hasPrevious = c.hasPreviousMediaItem(),
            hasNext = c.hasNextMediaItem(),
            shuffleEnabled = c.shuffleModeEnabled,
            repeatMode = c.repeatMode,
        )
    }

    private fun buildMediaItem(track: Track, streamUrl: String): MediaItem {
        val metadata = MediaMetadata.Builder()
            .setTitle(track.title)
            .setArtist(track.artist ?: "BestMusic")
            .setArtworkUri(track.thumbnail?.toUri())
            .setMediaType(MediaMetadata.MEDIA_TYPE_MUSIC)
            .build()
        return MediaItem.Builder()
            .setMediaId(track.id)
            .setUri(streamUrl)
            .setMediaMetadata(metadata)
            .build()
    }
}