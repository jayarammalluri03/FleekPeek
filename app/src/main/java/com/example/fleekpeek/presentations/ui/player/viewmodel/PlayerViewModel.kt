package com.example.fleekpeek.presentations.ui.player.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(application: Application) : AndroidViewModel(application) {

    companion object {
        const val Video_1 = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
        const val Video_2 = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4"
        const val Video_3 = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4"
        const val Video_4 = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/TearsOfSteel.mp4"
    }

    val player: ExoPlayer = ExoPlayer.Builder(application).build().apply {
        repeatMode = ExoPlayer.REPEAT_MODE_OFF
        playWhenReady = false
    }

    private val mediaUrls = listOf(Video_1, Video_2, Video_3, Video_4)

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying

    private val _currentVideoIndex = MutableStateFlow(0L)
    val currentVideoIndex: StateFlow<Long> = _currentVideoIndex

    private val _duration = MutableStateFlow(0L)
    val duration: StateFlow<Long> = _duration

    private val _playbackState = MutableStateFlow(Int.MIN_VALUE)
    val playbackState: StateFlow<Int> = _playbackState

    private val _isBuffering = MutableStateFlow(false)
    val isBuffering: StateFlow<Boolean> = _isBuffering


    init {
        preparePlaylist()
        initPlayerListner()
    }

    private fun initPlayerListner() {
        player.addListener(object : Player.Listener{
            override fun onIsPlayingChanged(isPlaying: Boolean) {
                super.onIsPlayingChanged(isPlaying)
                _isPlaying.value= isPlaying
            }
            override fun onPlaybackStateChanged(state: Int) {
                _playbackState.value = state
                _isBuffering.value = (state == Player.STATE_BUFFERING)
            }
        })
    }

    private fun preparePlaylist() {
        viewModelScope.launch() {
            val items = mediaUrls.map { url -> MediaItem.fromUri(url) }
            player.setMediaItems(items)
            player.prepare()
            launch {
                while (isActive){
                    val pos= player.currentPosition
                    val dur  = player.duration.coerceAtLeast(0L)
                    val playing = player.isPlaying

                    _currentVideoIndex.value= pos
                    _duration.value= dur
                    _isPlaying.value= playing
                    delay(500)
                }
            }
            player.play()
        }
    }

    fun play() {
        player.playWhenReady = true
        player.play()
        _isPlaying.value= true
    }

    fun pause() {
        player.playWhenReady = false
        player.pause()
        _isPlaying.value= false
    }

    fun seekTo(index: Long) {
        if(index > 0)
            player.seekTo(index.coerceAtLeast(0L).toLong())
    }


    fun skipForward(ms: Long = 10000L) {
        val newPos = (player.currentPosition + ms).coerceAtMost(player.duration.coerceAtLeast(player.currentPosition))
        player.seekTo(newPos)
    }

    fun skipBackward(ms: Long = 10000L) {
        val newPos = (player.currentPosition - ms).coerceAtLeast(0L)
        player.seekTo(newPos)
    }


    override fun onCleared() {
        super.onCleared()
        player.release()
    }

    private val previousRestartThresholdMs = 3_000L // 3 seconds
    var loopPlaylist: Boolean = false

    fun previous() {
        val currentIndex = player.currentMediaItemIndex
        val pos = player.currentPosition

        if (pos > previousRestartThresholdMs) {
            // restart current item
            player.seekTo(0L)
            play() // ensure playWhenReady true and playing
            return
        }

        // if we are at start-of-item and not enough pos, go to previous item if exists
        if (currentIndex > 0) {
            val prevIndex = currentIndex - 1
            player.seekTo(prevIndex, 0L)
            play()
        } else {
            // at first item already: restart
            player.seekTo(0L)
            play()
        }
    }

    fun next() {
        val currentIndex = player.currentMediaItemIndex
        val lastIndex = player.mediaItemCount - 1

        if (currentIndex < lastIndex) {
            val nextIndex = currentIndex + 1
            player.seekTo(nextIndex, 0L)
            play()
        } else {
            // at end
            if (loopPlaylist && player.mediaItemCount > 0) {
                player.seekTo(0, 0L)
                play()
            } else {
           }
        }
    }

}