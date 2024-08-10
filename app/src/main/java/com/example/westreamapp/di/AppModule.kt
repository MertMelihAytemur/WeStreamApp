package com.example.westreamapp.di

import com.example.westreamapp.videochat.VideoCallingApp
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import com.example.westreamapp.videochat.VideoCallViewModel
import com.example.westreamapp.connect.ConnectViewModel

val appModule = module {
    factory {
        val app = androidContext().applicationContext as VideoCallingApp
        app.client
    }

    viewModelOf(::ConnectViewModel)
    viewModelOf(::VideoCallViewModel)
}