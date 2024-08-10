package com.example.westreamapp.connect

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.example.westreamapp.videochat.VideoCallingApp

class ConnectViewModel(
    private val application: Application
) : AndroidViewModel(application) {

    var state by mutableStateOf(ConnectState())
        private set

    fun onAction(action: ConnectAction) {
        when (action) {
            ConnectAction.OnConnectClick -> {

            }

            is ConnectAction.OnNameChanged -> {

            }
        }
    }

    private fun connectToRoom() {
        state = state.copy(errorMessage = null)

        if(state.name.isBlank()){
            state = state.copy(errorMessage = "Username cannot be blank")
            return
        }

        //Init video client
        (application as VideoCallingApp).initVideoClient(username = state.name)
        state = state.copy(isConnected = true)
    }
}