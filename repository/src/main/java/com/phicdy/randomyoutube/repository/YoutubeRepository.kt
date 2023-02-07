package com.phicdy.randomyoutube.repository

import android.util.Log
import com.phicdy.randomyoutube.domain.model.Video
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class YoutubeRepository {

    suspend fun fetch(): List<Video>? = withContext(Dispatchers.IO) {
        val channelId = "UCFBjsYvwX7kWUjQoW7GcJ5A"
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.googleapis.com")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
        val api = retrofit.create(YoutubeApi::class.java)

        return@withContext try {
            val response = api.fetchChannel(
                apiKey = BuildConfig.YOUTUBE_API_KEY,
                part = "id,snippet,brandingSettings,contentDetails",
                channelId = channelId,
                maxResults = 5
            )
            val videoResponse = api.fetchPlaylistItems(
                apiKey = BuildConfig.YOUTUBE_API_KEY,
                part = "id,snippet",
                playlistId = response.items.first().contentDetails.relatedPlaylists.uploads,
                maxResults = 50,
                pageToken = null
            )
            val list = videoResponse.items.map { item ->
                Video(
                    id = item.snippet.resourceId.videoId,
                    title = item.snippet.title
                )
            }
            return@withContext list
        } catch (e: Exception) {
            Log.e("phicdyphicdy", e.toString())
            null
        }
    }
}