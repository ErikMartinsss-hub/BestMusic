package com.bestmusic.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.bestmusic.data.local.Playlist
import com.bestmusic.data.local.PlaylistRepository
import com.bestmusic.data.local.PlaylistTrack
import com.bestmusic.data.model.Track
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PlaylistViewModel(application: Application) : AndroidViewModel(application) {

    private val repo = PlaylistRepository(application)

    private val _playlists = MutableStateFlow<List<Playlist>>(emptyList())
    val playlists: StateFlow<List<Playlist>> = _playlists.asStateFlow()

    private val _selectedPlaylistTracks = MutableStateFlow<List<PlaylistTrack>>(emptyList())
    val selectedPlaylistTracks: StateFlow<List<PlaylistTrack>> = _selectedPlaylistTracks.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        loadPlaylists()
    }

    private fun loadPlaylists() {
        viewModelScope.launch {
            repo.getAllPlaylists().collect { list ->
                _playlists.value = list
            }
        }
    }

    fun selectPlaylist(playlistId: Long) {
        viewModelScope.launch {
            repo.getPlaylistTracks(playlistId).collect { tracks ->
                _selectedPlaylistTracks.value = tracks
            }
        }
    }

    fun clearSelectedPlaylist() {
        _selectedPlaylistTracks.value = emptyList()
    }

    fun createPlaylist(name: String) {
        viewModelScope.launch {
            try {
                repo.createPlaylist(name)
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Erro ao criar playlist: ${e.message}"
            }
        }
    }

    fun renamePlaylist(id: Long, newName: String) {
        viewModelScope.launch {
            try {
                repo.renamePlaylist(id, newName)
            } catch (e: Exception) {
                _error.value = "Erro ao renomear: ${e.message}"
            }
        }
    }

    fun deletePlaylist(id: Long) {
        viewModelScope.launch {
            try {
                repo.deletePlaylist(id)
            } catch (e: Exception) {
                _error.value = "Erro ao excluir: ${e.message}"
            }
        }
    }

    fun addTrackToPlaylist(playlistId: Long, track: Track) {
        viewModelScope.launch {
            try {
                repo.addTrackToPlaylist(playlistId, track)
                _error.value = "Adicionado à playlist"
            } catch (e: Exception) {
                _error.value = "Erro ao adicionar: ${e.message}"
            }
        }
    }

    fun removeTrackFromPlaylist(playlistId: Long, trackId: String) {
        viewModelScope.launch {
            try {
                repo.removeTrackFromPlaylist(playlistId, trackId)
            } catch (e: Exception) {
                _error.value = "Erro ao remover: ${e.message}"
            }
        }
    }

    suspend fun isTrackInPlaylist(playlistId: Long, trackId: String): Boolean =
        repo.isTrackInPlaylist(playlistId, trackId)
}