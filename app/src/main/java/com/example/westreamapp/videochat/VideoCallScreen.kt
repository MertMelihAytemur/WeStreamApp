package com.example.westreamapp.videochat

import android.os.Build
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import io.getstream.video.android.compose.permission.rememberCallPermissionsState
import io.getstream.video.android.compose.ui.components.call.activecall.CallContent


@Composable
fun VideoCallScreen(
    state: VideoCallState,
    onAction: (VideoCallAction) -> Unit
) {
    when {
        state.errorMessage != null -> {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = state.errorMessage, color = MaterialTheme.colorScheme.error)
            }
        }

        state.callState == CallState.JOINING -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
                Text(text = "Joining")
            }
        }

        else -> {
            val basePermissionList = listOf(
                android.Manifest.permission.CAMERA,
                android.Manifest.permission.RECORD_AUDIO
            )

            val bluetoothConnect = if (Build.VERSION.SDK_INT >= 31) {
                listOf(android.Manifest.permission.BLUETOOTH_CONNECT)
            } else emptyList()

            val notificationPermission = if (Build.VERSION.SDK_INT >= 33) {
                listOf(android.Manifest.permission.POST_NOTIFICATIONS)
            } else emptyList()

            val context = LocalContext.current

            CallContent(
                call = state.call,
                modifier = Modifier.fillMaxSize(),
                permissions = rememberCallPermissionsState(
                    call = state.call,
                    permissions = basePermissionList + bluetoothConnect + notificationPermission,
                    onPermissionsResult = { permissions ->
                        if(permissions.values.contains(false)){
                            Toast.makeText(
                                context,
                                "Please grant all permissions to join the call",
                                Toast.LENGTH_LONG
                            ).show()
                        }else {
                            onAction(VideoCallAction.onJoinCallClick)
                        }
                    }
                )
            )
        }
    }
}