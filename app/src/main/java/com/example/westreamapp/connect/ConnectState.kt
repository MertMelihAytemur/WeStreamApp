package com.example.westreamapp.connect

data class ConnectState(
    val name : String = "",
    val isConnected : Boolean = false,
    val errorMessage : String? = null
)
