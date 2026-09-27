package com.bestmusic.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Track(
    val id: String,
    val title: String,
    val artist: String? = null,
    val thumbnail: String? = null,
    val durationMs: Long = 0,
)