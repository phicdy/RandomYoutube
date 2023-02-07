package com.phicdy.randomyoutube

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import com.phicdy.randomyoutube.domain.model.Video
import com.phicdy.randomyoutube.ui.theme.RandomYoutubeTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            viewModel.fetch()
        }
        setContent {
            RandomYoutubeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    VideoList(
                        state = viewModel.state,
                        onVideoClicked = { videoId ->
                            startActivity(Intent(Intent.ACTION_VIEW).apply {
                                data = Uri.parse("vnd.youtube:$videoId")
                                putExtra("VIDEO_ID", videoId)
                            })
                        },
                        onRandomButtonClicked = { videos ->
                            val randomIndex = (videos.indices).random()
                            startActivity(Intent(Intent.ACTION_VIEW).apply {
                                data = Uri.parse("vnd.youtube:${videos[randomIndex].id}")
                                putExtra("VIDEO_ID", videos[randomIndex].id)
                            })
                        }
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
    onVideoClicked: (String) -> Unit,
    onRandomButtonClicked: (List<Video>) -> Unit,
) {
    VideoList(
        videos = state.value.videos,
        onVideoClicked = onVideoClicked,
        onRandomButtonClicked = onRandomButtonClicked
    )
}

@Composable
fun VideoList(
    modifier: Modifier = Modifier,
    videos: List<Video>,
    onVideoClicked: (String) -> Unit,
    onRandomButtonClicked: (List<Video>) -> Unit,
) {
    Column {
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
        LazyColumn {
            items(
                items = videos,
                key = { video -> video.id }
            ) { video ->
                Text(
                    text = video.title,
                    modifier = modifier.clickable { onVideoClicked(video.id) }
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
            onRandomButtonClicked = {}
        )
    }
}