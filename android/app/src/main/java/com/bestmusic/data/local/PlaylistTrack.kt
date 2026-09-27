package com.bestmusic.data.local

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import kotlinx.serialization.Serializable

@Entity(
    tableName = "playlist_tracks",
    primaryKeys = ["playlistId", "trackId"],
    foreignKeys = [
        ForeignKey(
            entity = Playlist::class,
            parentColumns = ["id"],
            childColumns = ["playlistId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("playlistId"), Index("trackId")]
)
@Serializable
data class PlaylistTrack(
    val playlistId: Long,
    val trackId: String,
    val addedAt: Long = System.currentTimeMillis(),
    val title: String,
    val artist: String?,
    val thumbnail: String?,
    val durationMs: Long,
)