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
        viewModelScope.launch(Dispatchers.IO) {
            repository.fetch().collect { videoList ->
                mutableState.value = MainState(videoList, null, false)
            }
        }
    }

    private val mutableState = mutableStateOf(MainState(listOf(), null, false))
    val state: State<MainState> = mutableState

    fun fetch() {
//        viewModelScope.launch(Dispatchers.IO) {
//            val response = repository.sync() ?: return@launch
//            mutableState.value = MainState(response, null, false)
//        }
    }

    fun onSelectRandomVideo(video: Video) {
        mutableState.value =
            mutableState.value.copy(randomSelectedVideo = video, showConfirmDialog = true)
    }

    fun onDismissDialog() {
        mutableState.value =
            mutableState.value.copy(randomSelectedVideo = null, showConfirmDialog = false)
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