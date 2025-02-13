package com.prabhat.movieapp.component

import android.net.Uri
import android.widget.MediaController
import android.widget.VideoView
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun VideoPlayer2(videoUri: Uri) {

    AndroidView(modifier = Modifier.fillMaxWidth().padding(16.dp).clip(RoundedCornerShape(16.dp)), factory = { context ->
        VideoView(context).apply {

            val mediaController = MediaController(context)
            mediaController.setAnchorView(this)
            setMediaController(mediaController)
            setVideoURI(videoUri)
            setOnPreparedListener {
                start()
            }
        }

    })

}
