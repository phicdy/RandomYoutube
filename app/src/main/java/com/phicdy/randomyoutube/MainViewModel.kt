package com.phicdy.randomyoutube

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request

class MainViewModel : ViewModel() {

    private val mutableState = mutableStateOf(MainState(""))
    val state: State<MainState> = mutableState

    fun fetch() {
        viewModelScope.launch(Dispatchers.IO) {
            val client = OkHttpClient()
            val channelId = "UCFBjsYvwX7kWUjQoW7GcJ5A"
            val request = Request.Builder()
                .url("https://www.googleapis.com/youtube/v3/channels?key=${BuildConfig.YOUTUBE_API_KEY}&part=id,snippet,brandingSettings,contentDetails&id=${channelId}&maxResults=1")
                .build()

            val response = try {
                client.newCall(request).execute()
            } catch (e: Exception) {
                null
            }
            mutableState.value = MainState(response?.body?.string())
        }
    }
}