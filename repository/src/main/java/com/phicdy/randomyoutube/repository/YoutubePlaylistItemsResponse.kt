package com.phicdy.randomyoutube.repository

data class YoutubePlaylistItemsResponse(
    val kind: String,
    val etag: String,
    val nextPageToken: String,
    val prevPageToken: String?,
    val items: List<YoutubePlaylistItemsItem>,
    val pageInfo: YoutubePlaylistPageInfo
)

data class YoutubePlaylistItemsItem(
    val kind: String,
    val etag: String,
    val id: String,
    val snippet: YoutubePlaylistItemsItemSnippet,
)

data class YoutubePlaylistItemsItemSnippet(
    val publishedAt: String,
    val etag: String?,
    val channelId: String,
    val title: String,
    val description: String,
    val thumbnails: YoutubePlaylistItemsItemSnippetThumbnails,
    val channelTitle: String,
    val playlistId: String,
    val position: Int,
    val resourceId: YoutubePlaylistItemsItemSnippetResourceId,
    val videoOwnerChannelTitle: String,
    val videoOwnerChannelId: String,
)

data class YoutubePlaylistItemsItemSnippetThumbnails(
    val default: YoutubePlaylistItemsItemSnippetThumbnail,
    val medium: YoutubePlaylistItemsItemSnippetThumbnail,
    val high: YoutubePlaylistItemsItemSnippetThumbnail,
    val standard: YoutubePlaylistItemsItemSnippetThumbnail,
    val maxres: YoutubePlaylistItemsItemSnippetThumbnail?,
)

data class YoutubePlaylistItemsItemSnippetThumbnail(
    val url: String,
    val width: Int,
    val height: Int,
)
data class YoutubePlaylistItemsItemSnippetResourceId(
    val kind: String,
    val videoId: String,
)

data class YoutubePlaylistPageInfo(
    val totalResults: Int,
    val resultsPerPage: Int,
)
