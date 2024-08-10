package com.example.westreamapp.videochat

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.getstream.video.android.core.StreamVideo
import kotlinx.coroutines.launch

class VideoCallViewModel(
    private val videoClient: StreamVideo
) : ViewModel() {

    var state by mutableStateOf(
        VideoCallState(
            call = videoClient.call("default", "main-room")
        )
    )
        private set


    fun onAction(action: VideoCallAction) {
        when (action) {
            VideoCallAction.onJoinCallClick -> {
                joinCall()
            }

            VideoCallAction.onDisconnectClick -> {
                state.call.leave()
                videoClient.logOut()
                state = state.copy(callState = CallState.ENDED)
            }
        }
    }

    private fun joinCall() {
        if (state.callState == CallState.ACTIVE) {
            return
        }

        viewModelScope.launch {
            state = state.copy(callState = CallState.JOINING)

            val shouldCreate =
                videoClient.queryCalls(filters = emptyMap()).getOrNull()?.calls?.isEmpty() == true

            state.call.join(create = shouldCreate).onSuccess {
                state = state.copy(
                    callState = CallState.ACTIVE, errorMessage = null
                )
            }.onError {
                state = state.copy(
                    callState = null, errorMessage = it.message
                )
            }
        }
    }
}