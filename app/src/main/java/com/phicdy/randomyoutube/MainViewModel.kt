package com.phicdy.randomyoutube

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.phicdy.randomyoutube.domain.model.Video
import com.phicdy.randomyoutube.repository.YoutubeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: YoutubeRepository
) : ViewModel() {

    init {
        viewModelScope.launch(Dispatchers.Main) {
            repository.fetch().collect { videoList ->
                mutableState.value = MainState(videoList, listOf(), false)
            }
        }
    }

    private val mutableState = mutableStateOf(MainState(listOf(), listOf(), false))
    val state: State<MainState> = mutableState

    fun sync() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.sync()
        }
    }

    fun onSelectRandomVideo(videos: List<Video>) {
        val list = ArrayList<Video>(50)
        repeat(50) {
            val randomIndex = (videos.indices).random()
            list.add(videos[randomIndex])
        }
        mutableState.value =
            mutableState.value.copy(selectedVideos = list, showConfirmDialog = true)
    }

    fun onDismissDialog() {
        mutableState.value =
            mutableState.value.copy(selectedVideos = listOf(), showConfirmDialog = false)
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val application = checkNotNull(extras[APPLICATION_KEY])
                return MainViewModel(
                    YoutubeRepository(application.applicationContext)
                ) as T
            }
        }
    }
}