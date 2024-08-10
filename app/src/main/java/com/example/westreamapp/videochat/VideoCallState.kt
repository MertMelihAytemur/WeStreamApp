package com.example.westreamapp.videochat

import io.getstream.video.android.core.Call


data class VideoCallState(
    val call : Call,
    val callState : CallState? = null,
    val errorMessage : String? = null
)

enum class CallState {
    JOINING,
    ACTIVE,
    ENDED
}
