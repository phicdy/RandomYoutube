package com.phicdy.randomyoutube.repository

data class YoutubeChannelResponse(
    val kind: String,
    val etag: String,
    val pageInfo: YoutubeChannelPageInfo,
    val items: List<YoutubeChannelItem>
)

data class YoutubeChannelPageInfo(
    val totalResults: Int,
    val resultsPerPage: Int,
)

data class YoutubeChannelItem(
    val kind: String,
    val etag: String,
    val id: String,
    val snippet: YoutubeChannelItemSnippet,
    val contentDetails: YoutubeChannelContentDetails,
    val brandingSettings: YoutubeChannelBrandingSettings
)

data class YoutubeChannelItemSnippet(
    val title: String,
    val description: String,
    val customUrl: String,
    val publishedAt: String,
    val thumbnails: YoutubeChannelItemSnippetThumbnails,
    val localized: YoutubeChannelItemSnippetLocalized,
    val country: String,
)

data class YoutubeChannelItemSnippetThumbnails(
    val default: YoutubeChannelItemSnippetThumbnail,
    val medium: YoutubeChannelItemSnippetThumbnail,
    val high: YoutubeChannelItemSnippetThumbnail,
)

data class YoutubeChannelItemSnippetThumbnail(
    val url: String,
    val width: Int,
    val height: Int,
)
data class YoutubeChannelItemSnippetLocalized(
    val title: String,
    val description: String,
)

data class YoutubeChannelContentDetails(
    val relatedPlaylists: YoutubeChannelRelatedPlaylists
)

data class YoutubeChannelRelatedPlaylists(
    val likes: String,
    val uploads: String
)

data class YoutubeChannelBrandingSettings(
    val channel: YoutubeChannelBrandingSettingsChannel,
    val image: YoutubeChannelBrandingSettingsImage
)

data class YoutubeChannelBrandingSettingsChannel(
    val title: String,
    val description: String,
    val keywords: String,
    val unsubscribedTrailer: String,
    val country: String,
)

data class YoutubeChannelBrandingSettingsImage(
    val bannerExternalUrl: String,
)
