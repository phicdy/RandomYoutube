package com.phicdy.randomyoutube.repository

import android.util.Log
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class YoutubeRepository {

    suspend fun fetch(): YoutubePlaylistItemsResponse? = withContext(Dispatchers.IO) {
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
            api.fetchPlaylistItems(
                apiKey = BuildConfig.YOUTUBE_API_KEY,
                part = "id,snippet",
                playlistId = response.items.first().contentDetails.relatedPlaylists.uploads,
                maxResults = 50,
                pageToken = null
            )
        } catch (e: Exception) {
            Log.e("phicdyphicdy", e.toString())
            null
        }
    }
}