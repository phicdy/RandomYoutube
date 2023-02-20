package com.phicdy.randomyoutube

import com.phicdy.randomyoutube.domain.model.Video

data class MainState(
    val allVideos: List<Video>,
    val selectedVideos: List<Video>
)


