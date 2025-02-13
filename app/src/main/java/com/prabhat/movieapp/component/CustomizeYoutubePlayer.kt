package com.prabhat.movieapp.component


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@Composable
fun CustomYoutubePlayer(youtubeVideoId: String) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    var lifecycle by remember { mutableStateOf(Lifecycle.Event.ON_CREATE) }
    var youTubePlayer: YouTubePlayer? by remember { mutableStateOf(null) }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            lifecycle = event
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(10.dp))
            .border(
                border = BorderStroke(
                    1.dp, brush = Brush.sweepGradient(
                        listOf(Color.White, Color.Red, Color.Green, Color.Yellow)
                    )
                )
            ),
        factory = { ctx ->
            YouTubePlayerView(ctx).apply {

                enableAutomaticInitialization = false
                lifecycleOwner.lifecycle.addObserver(this)
                initialize(object : AbstractYouTubePlayerListener() {
                    override fun onReady(player: YouTubePlayer) {
                        youTubePlayer = player
                        player.loadVideo(youtubeVideoId, 0f)
                        player.play()
                    }
                }, getPlayerOptions()) // Customization options
            }
        },
        update = { view ->
            when (lifecycle) {
                Lifecycle.Event.ON_RESUME -> youTubePlayer?.play()
                Lifecycle.Event.ON_PAUSE -> youTubePlayer?.pause()
                else -> Unit
            }
        }
    )
}

/**
 * Custom player options to hide controls and enable auto-play.
 */
private fun getPlayerOptions(): IFramePlayerOptions {
    return IFramePlayerOptions.Builder()
        .controls(0) // Hide YouTube controls
        .fullscreen(1) // Allow fullscreen
        .autoplay(0) // Auto-play on start
        .fullscreen(1)
        .ccLoadPolicy(1)
        .ivLoadPolicy(1)
        .mute(1)
        .build()
}
