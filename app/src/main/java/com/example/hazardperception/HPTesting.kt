package com.example.hazardperception

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.background
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.compose.ui.viewinterop.AndroidView
import com.example.hazardperception.ui.theme.HazardPerceptionAppTheme

@Composable
fun HazardDetection(videoUri: String) {
    var flagPositions by remember { mutableStateOf(listOf<Float>()) } // Store relative positions
    var startTime by remember { mutableStateOf<Long?>(null) }

    // Column to arrange video and flags vertically
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top // Keep video at the top
    ) {
        // Video Player with Black Background Box
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp) // Adjust the height as per the desired video size
                .background(Color.Black)
                .pointerInput(Unit) {
                    detectTapGestures { tapOffset ->
                        // Get the current timestamp in milliseconds
                        val currentTime = System.currentTimeMillis()

                        // If it's the first time, start the timer
                        if (startTime == null) {
                            startTime = currentTime
                        } else {
                            // Calculate the elapsed time since the start
                            val elapsedTime = currentTime - startTime!!

                            // Map elapsed time (0 to 12000ms) to 0 to 1.0f for position on screen
                            val relativePosition = elapsedTime.toFloat() / 13000f // 12 seconds video duration

                            // Add the position (between 0.0 and 1.0) to the list
                            flagPositions = flagPositions + relativePosition
                        }
                    }
                }
        ) {
            VideoPlayer(videoUri = videoUri, modifier = Modifier.fillMaxSize())
        }

        Spacer(modifier = Modifier.height(4.dp)) // Add space between video and flags

        // Box to contain the flags (located at the bottom of the screen)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp) // Height of the flag container (adjustable)
                .background(Color.White) // Background for flag box
        ) {
            // Display Flag Markers below the video at the bottom section
            flagPositions.forEach { position ->
                FlagMarker(relativePosition = position)
            }
        }
    }
}

@Composable
fun FlagMarker(relativePosition: Float, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .offset(x = (relativePosition * 1000).dp) // Map relative position to screen space (x-axis)
            .size(40.dp) // Size of the flag marker (adjust as needed)
    ) {
        Image(
            painter = painterResource(id = R.drawable.flag_icon), // PNG flag image from drawable folder
            contentDescription = "Flag Marker",
            modifier = Modifier.fillMaxSize() // Fill the Box with the PNG image
        )
    }
}
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun VideoPlayer(videoUri: String, modifier: Modifier = Modifier) {
    val context = LocalContext.current

    // Initialize ExoPlayer
    val exoPlayer = ExoPlayer.Builder(context).build().apply {
        setMediaItem(androidx.media3.common.MediaItem.fromUri(Uri.parse(videoUri)))
        prepare()
        playWhenReady = true  // Automatically start video
    }

    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }
    }

    // Render the PlayerView
    AndroidView(
        modifier = modifier,
        factory = {
            PlayerView(context).apply {
                player = exoPlayer
                useController = false  // Remove player controls
            }
        }
    )
}

@Composable
fun MainActivityScreen() {
    val context = LocalContext.current

    HazardDetection(
        videoUri = "android.resource://${context.packageName}/raw/hazard_video.mp4"
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    HazardPerceptionAppTheme {
        MainActivityScreen()
    }
}