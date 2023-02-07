package com.phicdy.randomyoutube

data class MainState(
    val videos: List<Video>
) {
    data class Video(
        val id: String,
        val title: String,
    )
}


