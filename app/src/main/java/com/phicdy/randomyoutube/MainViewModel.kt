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

    private val mutableState = mutableStateOf(MainState(listOf()))
    val state: State<MainState> = mutableState

    private val repository = YoutubeRepository()

    fun fetch() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.fetch()
            val list = response?.items?.map { item ->
                Video(
                    id = item.snippet.resourceId.videoId,
                    title = item.snippet.title
                )
            } ?: return@launch
            mutableState.value = MainState(list)
        }
    }
}