    package com.example.fleekpeek.presentations.ui.player

    import android.app.Activity
    import android.content.pm.ActivityInfo
    import android.content.pm.ActivityInfo.*
    import android.view.ViewGroup.LayoutParams.MATCH_PARENT
    import androidx.compose.animation.AnimatedVisibility
    import androidx.compose.foundation.Canvas
    import androidx.compose.foundation.background
    import androidx.compose.foundation.gestures.detectDragGestures
    import androidx.compose.foundation.gestures.detectTapGestures
    import androidx.compose.foundation.layout.Arrangement
    import androidx.compose.foundation.layout.Box
    import androidx.compose.foundation.layout.Column
    import androidx.compose.foundation.layout.Row
    import androidx.compose.foundation.layout.Spacer
    import androidx.compose.foundation.layout.fillMaxSize
    import androidx.compose.foundation.layout.fillMaxWidth
    import androidx.compose.foundation.layout.height
    import androidx.compose.foundation.layout.offset
    import androidx.compose.foundation.layout.padding
    import androidx.compose.foundation.layout.size
    import androidx.compose.foundation.shape.CircleShape
    import androidx.compose.material.icons.Icons
    import androidx.compose.material.icons.filled.ArrowBack
    import androidx.compose.material.icons.filled.ArrowForward
    import androidx.compose.material.icons.filled.Close
    import androidx.compose.material.icons.filled.LocationOn
    import androidx.compose.material.icons.filled.PlayArrow
    import androidx.compose.material.icons.filled.Refresh
    import androidx.compose.material3.CircularProgressIndicator
    import androidx.compose.material3.Icon
    import androidx.compose.material3.IconButton
    import androidx.compose.material3.MaterialTheme
    import androidx.compose.material3.Slider
    import androidx.compose.material3.SliderDefaults
    import androidx.compose.material3.Text
    import androidx.compose.runtime.Composable
    import androidx.compose.runtime.DisposableEffect
    import androidx.compose.runtime.LaunchedEffect
    import androidx.compose.runtime.collectAsState
    import androidx.compose.runtime.getValue
    import androidx.compose.runtime.mutableStateOf
    import androidx.compose.runtime.remember
    import androidx.compose.runtime.setValue
    import androidx.compose.ui.Alignment
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.geometry.CornerRadius
    import androidx.compose.ui.geometry.Size
    import androidx.compose.ui.graphics.Color
    import androidx.compose.ui.graphics.toArgb
    import androidx.compose.ui.input.pointer.pointerInput
    import androidx.compose.ui.layout.onSizeChanged
    import androidx.compose.ui.platform.LocalContext
    import androidx.compose.ui.unit.IntOffset
    import androidx.compose.ui.unit.dp
    import androidx.compose.ui.viewinterop.AndroidView
    import androidx.hilt.navigation.compose.hiltViewModel
    import androidx.media3.common.util.Util
    import androidx.media3.ui.PlayerView
    import com.example.fleekpeek.presentations.ui.player.viewmodel.PlayerViewModel

    @Composable
    fun PlayerScreen(viewModel: PlayerViewModel= hiltViewModel()) {

        var player = viewModel.player


        val activity = (LocalContext.current as? Activity)

        val previousOrientation = remember { activity?.requestedOrientation ?: SCREEN_ORIENTATION_UNSPECIFIED }

        val isPlaying by viewModel.isPlaying.collectAsState()
        val currentPosition by viewModel.currentVideoIndex.collectAsState()
        val dur by viewModel.duration.collectAsState()
        val showProgress by viewModel.isBuffering.collectAsState()

        var sliderPosition by remember { mutableStateOf(0f) }     // 0..1 fraction
        var isUserSeeking by remember { mutableStateOf(false) }

        var sliderFraction by remember { mutableStateOf(0f) }

        val playerViewRef = remember { arrayOfNulls<PlayerView>(1) }

        var controlsVisible by remember { mutableStateOf(true) }
        var touchCounter by remember { mutableStateOf(0) }

        LaunchedEffect(controlsVisible, isUserSeeking, touchCounter) {
            if (controlsVisible && !isUserSeeking) {
                kotlinx.coroutines.delay(3000L)
                if (controlsVisible && !isUserSeeking) {
                    controlsVisible = false
                }
            }
        }

        val window = activity?.window
        val view = LocalContext.current as Activity

        DisposableEffect(Unit) {
            val insetsController = androidx.core.view.WindowInsetsControllerCompat(window!!, window.decorView)

            insetsController.hide(androidx.core.view.WindowInsetsCompat.Type.systemBars())

            insetsController.systemBarsBehavior =
                androidx.core.view.WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

            window.statusBarColor = Color.Transparent.toArgb()
            window.navigationBarColor = Color.Transparent.toArgb()

            onDispose {
                // Show system bars back when leaving player
                insetsController.show(androidx.core.view.WindowInsetsCompat.Type.systemBars())

                // Reset bar colors (optional)
                window.statusBarColor = Color.Black.toArgb()
                window.navigationBarColor = Color.Black.toArgb()
            }
        }

        if(!isUserSeeking && dur > 0)
            sliderFraction = currentPosition.toFloat() / dur.toFloat()


        DisposableEffect(Unit) {
            activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE

            onDispose {
                activity?.requestedOrientation = previousOrientation
            }
        }

        Box(
           modifier = Modifier.fillMaxSize().pointerInput(Unit){

                   detectTapGestures (
                       onTap = {
                           controlsVisible= !controlsVisible
                           touchCounter++
                       }
                   )

           }
       ) {
            AndroidView(
                factory = { ctx ->
                    PlayerView(ctx).apply {
                        layoutParams =
                            android.view.ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT)
                        useController = false
                        player = viewModel.player
                        playerViewRef[0] = this
                    }
                },
                update = { pv ->
                    if (pv.player != player) {
                        pv.player = player
                    }
                },
                modifier = Modifier.fillMaxSize()
            )
            AnimatedVisibility(
                visible = controlsVisible,
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
                Box(modifier = Modifier.)
                Column(
                    modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter).background(
                        Color(0x66000000)
                    ).padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val dur = dur.coerceAtLeast(1L)
                    if (!isUserSeeking) {
                        sliderPosition =
                            if (dur > 0) currentPosition.toFloat() / dur.toFloat() else 0F
                    }
                    YouTubeSeekBar(
                        progress = sliderPosition,
                        onSeek = { fraction ->
                            isUserSeeking = true
                            sliderFraction = fraction
                        },
                        onSeekFinished = { fraction ->
                            val seekMs = (fraction * dur).toLong()
                            viewModel.seekTo(seekMs)
                            isUserSeeking = false
                        }
                    )
                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // left time
                        Text(text = formatMillis(currentPosition), color = Color.White, style = MaterialTheme.typography.bodySmall)

                        // controls cluster (Prev, Rewind, Play/Pause, Forward, Next)
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            IconButton(onClick = {
                                viewModel.previous()
                                touchCounter++
                            }) {
                                Icon(Icons.Default.ArrowBack, contentDescription = "Previous", tint = Color.White)
                            }

                            IconButton(onClick = {
                                viewModel.skipBackward(10_000L); touchCounter++
                            }) {
                                Icon(Icons.Default.Refresh, contentDescription = "Rewind 10s", tint = Color.White)
                            }

                            IconButton(
                                onClick = {
                                    if (isPlaying) viewModel.pause() else viewModel.play()
                                    touchCounter++
                                },
                                modifier = Modifier
                                    .size(56.dp)
                                    .background(MaterialTheme.colorScheme.primary, CircleShape)
                            ) {
                                if (isPlaying) Icon(Icons.Default.LocationOn, contentDescription = "Pause", tint = Color.White)
                                else Icon(Icons.Default.PlayArrow, contentDescription = "Play", tint = Color.White)
                            }

                            IconButton(onClick = {
                                viewModel.skipForward(10_000L); touchCounter++
                            }) {
                                Icon(Icons.Default.Refresh, contentDescription = "Forward 10s", tint = Color.White)
                            }

                            IconButton(onClick = {
                                viewModel.next(); touchCounter++
                            }) {
                                Icon(Icons.Default.ArrowForward, contentDescription = "Next", tint = Color.White)
                            }
                        }

                        // right time
                        Text(text = formatMillis(dur), color = Color.White, style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
        DisposableEffect(key1 = Unit) {
            onDispose {
                playerViewRef[0]?.player= null
                playerViewRef[0]= null
            }
        }
    }

    @Composable
    fun YouTubeSeekBar(
        progress: Float,             // 0f..1f
        onSeek: (Float) -> Unit,     // called continuously
        onSeekFinished: (Float) -> Unit, // user released
    ) {
        val knobRadius = 8.dp
        val barHeight = 3.dp

        var widthPx by remember { mutableStateOf(1f) }
        var dragging by remember { mutableStateOf(false) }
        var dragX by remember { mutableStateOf(0f) }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(24.dp)   // touch area (not visual thickness)
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragStart = { pos ->
                            dragging = true
                            dragX = pos.x
                        },
                        onDrag = { change, _ ->
                            dragX = change.position.x
                            val newProgress = (dragX / widthPx).coerceIn(0f, 1f)
                            onSeek(newProgress)
                        },
                        onDragEnd = {
                            dragging = false
                            onSeekFinished((dragX / widthPx).coerceIn(0f, 1f))
                        }
                    )
                }
        ) {

            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
                    .height(barHeight)
                    .onSizeChanged { widthPx = it.width.toFloat() }
            ) {
                val playedWidth = widthPx * progress

                // background track
                drawRoundRect(
                    color = Color.Gray.copy(alpha = 0.5f),
                    size = Size(widthPx, barHeight.toPx()),
                    cornerRadius = CornerRadius(4f, 4f)
                )

                // played progress track (red)
                drawRoundRect(
                    color = Color.Red,
                    size = Size(playedWidth, barHeight.toPx()),
                    cornerRadius = CornerRadius(4f, 4f)
                )
            }

            // knob
            val knobCenter = (widthPx * progress).coerceIn(0f, widthPx)

            Box(
                modifier = Modifier
                    .offset { IntOffset(knobCenter.toInt() - knobRadius.toPx().toInt(), 0) }
                    .size(knobRadius * 2)
                    .align(Alignment.CenterStart)
                    .background(Color.Red, CircleShape)
            )
        }
        }

    private fun formatMillis(ms: Long): String {
        if (ms <= 0L) return "00:00"
        val totalSeconds = (ms / 1000).toInt()
        val seconds = totalSeconds % 60
        val minutes = (totalSeconds / 60) % 60
        val hours = totalSeconds / 3600
        return if (hours > 0) String.format("%d:%02d:%02d", hours, minutes, seconds)
        else String.format("%02d:%02d", minutes, seconds)
    }
