package com.phicdy.randomyoutube

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.phicdy.randomyoutube.ui.theme.RandomYoutubeTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

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
                        onVideoClicked = { videoId ->
                            startActivity(Intent(Intent.ACTION_VIEW).apply {
                                data = Uri.parse("vnd.youtube:$videoId")
                                putExtra("VIDEO_ID", videoId);
                            })
                        }
                    )
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            viewModel.fetch()
        }
    }
}

@Composable
fun VideoList(
    modifier: Modifier = Modifier,
    state: State<MainState>,
    onVideoClicked: (String) -> Unit
) {
    LazyColumn {
        items(
            items = state.value.videos,
            key = { video -> video.id }
        ) { video ->
            Text(
                text = video.title,
                modifier = modifier.clickable { onVideoClicked(video.id) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RandomYoutubeTheme {
//        Greeting("Android")
    }
}