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
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
                            val randomIndex = (videos.indices).random()
                            viewModel.onSelectRandomVideo(videos[randomIndex])
                        },
                        onSyncButtonClicked = {
                            viewModel.sync()
                        },
                        onDismissDialog = {
                            viewModel.onDismissDialog()
                        },
                        onOkClicked = { videoId ->
                            startActivity(Intent(Intent.ACTION_VIEW).apply {
                                data = Uri.parse("vnd.youtube:$videoId")
                                putExtra("VIDEO_ID", videoId)
                            })
                        },
                    )
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
    onDismissDialog: () -> Unit,
    onOkClicked: (String) -> Unit,
) {
    VideoList(
        videos = state.value.videos,
        onVideoClicked = onVideoClicked,
        onRandomButtonClicked = onRandomButtonClicked,
        onSyncButtonClicked = onSyncButtonClicked,
        onDismissDialog = onDismissDialog,
        onOkClicked = onOkClicked,
        randomSelectedVideo = state.value.randomSelectedVideo,
        showConfirmDialog = state.value.showConfirmDialog
    )
}

@Composable
fun VideoList(
    modifier: Modifier = Modifier,
    videos: List<Video>,
    onVideoClicked: (Video) -> Unit,
    onRandomButtonClicked: (List<Video>) -> Unit,
    onSyncButtonClicked: () -> Unit,
    onDismissDialog: () -> Unit,
    onOkClicked: (String) -> Unit,
    randomSelectedVideo: Video?,
    showConfirmDialog: Boolean
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
            items(
                items = videos,
                key = { video -> video.id }
            ) { video ->
                Text(
                    text = video.title,
                    modifier = modifier.clickable { onVideoClicked(video) }
                )
            }
        }
        if (showConfirmDialog && randomSelectedVideo != null) {
            AlertDialog(
                onDismissRequest = { onDismissDialog() },
                confirmButton = {
                    TextButton(
                        onClick = { onOkClicked(randomSelectedVideo.id) },
                    ) {
                        Text(text = "OK")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { onDismissDialog() }) {
                        Text(text = "Cancel")
                    }
                },
                title = {
                    Text(text = randomSelectedVideo.title)
                }
            )
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
            onDismissDialog = {},
            onOkClicked = {},
            randomSelectedVideo = null,
            showConfirmDialog = false
        )
    }
}