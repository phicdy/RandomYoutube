package com.phicdy.randomyoutube.repository

import retrofit2.http.GET
import retrofit2.http.Query

internal interface YoutubeApi {

    @GET("youtube/v3/channels")
    suspend fun fetchChannel(
        @Query("key") apiKey: String,
        @Query("part") part: String,
        @Query("id") channelId: String,
        @Query("maxResults") maxResults: Int
    ): YoutubeChannelResponse

    @GET("youtube/v3/playlistItems")
    suspend fun fetchPlaylistItems(
        @Query("key") apiKey: String,
        @Query("part") part: String,
        @Query("playlistId") playlistId: String,
        @Query("maxResults") maxResults: Int,
        @Query("pageToken") pageToken: String?,
    ): YoutubePlaylistItemsResponse
}