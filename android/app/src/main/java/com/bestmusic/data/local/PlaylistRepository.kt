package com.bestmusic.data.local

import android.content.Context
import com.bestmusic.data.model.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class PlaylistRepository(private val context: Context) {

    private val db = AppDatabase.getInstance(context)
    private val dao = db.playlistDao()

    fun getAllPlaylists(): Flow<List<Playlist>> = dao.getAllPlaylists()

    fun getPlaylist(id: Long): Flow<Playlist?> = dao.getPlaylist(id)

    fun getPlaylistTracks(playlistId: Long): Flow<List<PlaylistTrack>> = dao.getPlaylistTracks(playlistId)

    fun getPlaylistTracksAsTracks(playlistId: Long): Flow<List<Track>> =
        dao.getPlaylistTracks(playlistId).map { tracks ->
            tracks.map { pt ->
                Track(
                    id = pt.trackId,
                    title = pt.title,
                    artist = pt.artist,
                    thumbnail = pt.thumbnail,
                    durationMs = pt.durationMs
                )
            }
        }

    suspend fun createPlaylist(name: String): Long {
        val playlist = Playlist(name = name.trim())
        return dao.insertPlaylist(playlist)
    }

    suspend fun renamePlaylist(id: Long, newName: String) {
        dao.updatePlaylist(id, newName.trim(), System.currentTimeMillis())
    }

    suspend fun deletePlaylist(id: Long) {
        dao.deletePlaylist(id)
    }

    suspend fun addTrackToPlaylist(playlistId: Long, track: Track) {
        val pt = PlaylistTrack(
            playlistId = playlistId,
            trackId = track.id,
            title = track.title,
            artist = track.artist,
            thumbnail = track.thumbnail,
            durationMs = track.durationMs
        )
        dao.addTrackToPlaylist(pt)
        // Update playlist updatedAt
        val playlist = dao.getPlaylist(playlistId).first()
        playlist?.let { p ->
            dao.updatePlaylist(p.id, p.name, System.currentTimeMillis())
        }
    }

    suspend fun removeTrackFromPlaylist(playlistId: Long, trackId: String) {
        dao.removeTrackFromPlaylist(playlistId, trackId)
    }

    suspend fun isTrackInPlaylist(playlistId: Long, trackId: String): Boolean =
        dao.isTrackInPlaylist(playlistId, trackId)

    suspend fun getTrackCount(playlistId: Long): Int = dao.getTrackCount(playlistId)
}