package com.bestmusic.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.bestmusic.BestMusicApp
import com.bestmusic.MusicRepository
import com.bestmusic.data.model.Track
import com.bestmusic.player.PlayerManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repo: MusicRepository = (application as BestMusicApp).repository
    val player: PlayerManager = (application as BestMusicApp).playerManager

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    private val _results = MutableStateFlow<List<Track>>(emptyList())
    val results: StateFlow<List<Track>> = _results.asStateFlow()

    private val _searching = MutableStateFlow(false)
    val searching: StateFlow<Boolean> = _searching.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _loadingId = MutableStateFlow<String?>(null)
    val loadingId: StateFlow<String?> = _loadingId.asStateFlow()

    fun onQueryChange(value: String) {
        _query.value = value
    }

    fun search() {
        val q = _query.value.trim()
        if (q.isEmpty()) return
        viewModelScope.launch {
            _searching.value = true
            _error.value = null
            try {
                _results.value = repo.search(q)
            } catch (e: Exception) {
                _error.value = "Falha na busca: ${e.message ?: e.javaClass.simpleName}"
            } finally {
                _searching.value = false
            }
        }
    }

    fun play(track: Track) {
        viewModelScope.launch {
            _loadingId.value = track.id
            try {
                val stream = repo.resolveStream(track.id)
                val enriched = track.copy(
                    artist = stream.artist ?: track.artist ?: track.title,
                    durationMs = if (stream.durationMs > 0) stream.durationMs else track.durationMs,
                    thumbnail = stream.thumbnail ?: track.thumbnail,
                )
                player.play(enriched, stream.url)
            } catch (e: Exception) {
                _error.value = "Não foi possível reproduzir: ${e.message ?: e.javaClass.simpleName}"
            } finally {
                _loadingId.value = null
            }
        }
    }

    fun setServerUrl(url: String) {
        repo.setBaseUrl(url)
        _results.value = emptyList()
        search()
    }
}