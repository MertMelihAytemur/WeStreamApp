package com.example.westreamapp.videochat

sealed interface VideoCallAction {
    data object onDisconnectClick : VideoCallAction
    data object onJoinCallClick : VideoCallAction
}