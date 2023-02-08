package com.phicdy.randomyoutube

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.phicdy.randomyoutube.domain.model.Video
import com.phicdy.randomyoutube.repository.YoutubeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val mutableState = mutableStateOf(MainState(listOf(), null, false))
    val state: State<MainState> = mutableState

    private val repository = YoutubeRepository()

    fun fetch() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.fetch() ?: return@launch
            mutableState.value = MainState(response, null, false)
        }
    }

    fun onSelectRandomVideo(video: Video) {
        mutableState.value =
            mutableState.value.copy(randomSelectedVideo = video, showConfirmDialog = true)
    }

    fun onDismissDialog() {
        mutableState.value =
            mutableState.value.copy(randomSelectedVideo = null, showConfirmDialog = false)
    }
}