package com.phicdy.randomyoutube

import com.phicdy.randomyoutube.domain.model.Video

data class MainState(
    val videos: List<Video>,
    val randomSelectedVideo: Video?,
    val showConfirmDialog: Boolean
)


