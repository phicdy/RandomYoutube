package com.phicdy.randomyoutube.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

class YoutubeRepository {

    suspend fun fetch(): Response? = withContext(Dispatchers.IO) {
        val client = OkHttpClient()
        val channelId = "UCFBjsYvwX7kWUjQoW7GcJ5A"
        val request = Request.Builder()
            .url("https://www.googleapis.com/youtube/v3/channels?key=${BuildConfig.YOUTUBE_API_KEY}&part=id,snippet,brandingSettings,contentDetails&id=${channelId}&maxResults=1")
            .build()

        return@withContext try {
            client.newCall(request).execute()
        } catch (e: Exception) {
            null
        }
    }
}