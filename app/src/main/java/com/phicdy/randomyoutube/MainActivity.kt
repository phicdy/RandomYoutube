package com.phicdy.randomyoutube

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.phicdy.randomyoutube.domain.model.Video
import com.phicdy.randomyoutube.ui.theme.RandomYoutubeTheme

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels { MainViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RandomYoutubeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    VideoList(
                        state = viewModel.state,
                        onVideoClicked = { video ->
                            startActivity(Intent(Intent.ACTION_VIEW).apply {
                                data = Uri.parse("vnd.youtube:${video.id}")
                                putExtra("VIDEO_ID", video.id)
                            })
                        },
                        onRandomButtonClicked = { videos ->
                            viewModel.onSelectRandomVideo(videos)
                        },
                    ) {
                        viewModel.sync()
                    }
                }
            }
        }
    }
}

@Composable
fun VideoList(
    modifier: Modifier = Modifier,
    state: State<MainState>,
    onVideoClicked: (Video) -> Unit,
    onRandomButtonClicked: (List<Video>) -> Unit,
    onSyncButtonClicked: () -> Unit,
) {
    VideoList(
        videos = state.value.allVideos,
        onVideoClicked = onVideoClicked,
        onRandomButtonClicked = onRandomButtonClicked,
        onSyncButtonClicked = onSyncButtonClicked,
        selectedVideos = state.value.selectedVideos
    )
}

@Composable
fun VideoList(
    modifier: Modifier = Modifier,
    videos: List<Video>,
    onVideoClicked: (Video) -> Unit,
    onRandomButtonClicked: (List<Video>) -> Unit,
    onSyncButtonClicked: () -> Unit,
    selectedVideos: List<Video>
) {
    Column {
        Row {
            Button(
                modifier = modifier
                    .width(96.dp)
                    .height(48.dp),
                onClick = { onRandomButtonClicked(videos) }
            ) {
                Text(
                    modifier = modifier,
                    textAlign = TextAlign.Center,
                    text = "Random",
                    fontSize = 12.sp
                )
            }
            Button(
                modifier = modifier
                    .width(96.dp)
                    .height(48.dp),
                onClick = { onSyncButtonClicked() }
            ) {
                Text(
                    modifier = modifier,
                    textAlign = TextAlign.Center,
                    text = "Sync",
                    fontSize = 12.sp
                )
            }
        }
        LazyColumn {
            itemsIndexed(
                items = selectedVideos.ifEmpty { videos },
                key = { index, video -> "$index${video.id}" }
            ) { _, video ->
                Text(
                    text = video.title,
                    modifier = modifier.clickable { onVideoClicked(video) }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RandomYoutubeTheme {
        VideoList(
            videos = listOf(Video("hoge", "title"), Video("fuga", "title2")),
            onVideoClicked = {},
            onRandomButtonClicked = {},
            onSyncButtonClicked = {},
            selectedVideos = listOf()
        )
    }
}