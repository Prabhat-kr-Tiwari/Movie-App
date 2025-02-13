package com.prabhat.movieapp.component

import android.content.Context
import android.net.Uri
import android.util.SparseArray
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout
import androidx.annotation.OptIn
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.media3.common.C

import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.AssetDataSource
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DataSpec
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.hls.HlsMediaSource
import androidx.media3.exoplayer.source.MediaSource
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import com.prabhat.movieapp.R
import java.io.File


@OptIn(UnstableApi::class)
@Composable
fun VideoPlayer3(assetFileName: String) {

    var lifecycle by remember {

        mutableStateOf(Lifecycle.Event.ON_CREATE)
    }
    val context = LocalContext.current
    val assetFileDescriptor = context.assets.openFd(assetFileName)
    val dataSpec = DataSpec(Uri.fromFile(File(assetFileDescriptor.fileDescriptor.toString())))
    val assetDataSource = AssetDataSource(context).apply { open(dataSpec) }
    val dataSourceFactory = DataSource.Factory { assetDataSource }

  /*  val mediaItem =
        MediaItem.fromUri(uri)*/
    val mediaItem = MediaItem.fromUri(Uri.parse("asset:///$assetFileName"))

//        MediaItem.fromUri("https://devstreaming-cdn.apple.com/videos/streaming/examples/img_bipbop_adv_example_ts/master.m3u8")
/*    val mediaSource:MediaSource=HlsMediaSource.Factory(DefaultHttpDataSource.Factory())
        .createMediaSource(mediaItem)*/
    val mediaSource: MediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
        .createMediaSource(mediaItem)
    val exoPlayer = remember(context) {
        ExoPlayer.Builder(context).build()
            .apply {

                setMediaSource(mediaSource)
                prepare()
                playWhenReady = true
            }
    }

    exoPlayer.playWhenReady = true
    exoPlayer.videoScalingMode = C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING
    exoPlayer.repeatMode = Player.REPEAT_MODE_ONE

    val lifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current
    DisposableEffect(key1 = lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            lifecycle = event
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {

            exoPlayer.release()
            lifecycleOwner.lifecycle.removeObserver(observer)
        }

    }
    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(16f / 9f), factory = {
            PlayerView(context).also { playerView ->
                playerView.player = exoPlayer
                playerView.hideController()
                playerView.useController=false
                playerView.resizeMode=AspectRatioFrameLayout.RESIZE_MODE_ZOOM
                playerView.layoutParams=FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)

            }

        },
        update = {
            when (lifecycle) {

                Lifecycle.Event.ON_RESUME -> {
                    it.onPause()
                    it.player?.pause()
                }

                Lifecycle.Event.ON_PAUSE -> {
                    it.onResume()
                }

                else -> Unit
            }

        }
    )
}