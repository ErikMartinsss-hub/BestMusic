package com.bestmusic

import android.content.Context
import com.bestmusic.data.api.YtApi
import com.bestmusic.data.model.StreamInfo
import com.bestmusic.data.model.Track
import java.util.concurrent.TimeUnit
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

class MusicRepository(private val context: Context) {

    @Volatile
    private var api: YtApi = buildApi()

    private fun buildApi(): YtApi {
        val base = Settings.baseUrl(context).trim().trimEnd('/')
        val json = Json { ignoreUnknownKeys = true }
        val client = okhttp3.OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build()
        return Retrofit.Builder()
            .baseUrl("$base/")
            .client(client)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(YtApi::class.java)
    }

    fun setBaseUrl(url: String) {
        Settings.setBaseUrl(context, url)
        api = buildApi()
    }

    suspend fun search(query: String): List<Track> =
        api.search(query).filter { it.id.isNotBlank() && it.title.isNotBlank() }

    suspend fun resolveStream(id: String): StreamInfo = api.stream(id)
}