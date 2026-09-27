package com.bestmusic.data.api

import com.bestmusic.data.model.StreamInfo
import com.bestmusic.data.model.Track
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface YtApi {
    @GET("api/search")
    suspend fun search(@Query("q") query: String): List<Track>

    @GET("api/stream/{video_id}")
    suspend fun stream(@Path("video_id") videoId: String): StreamInfo
}