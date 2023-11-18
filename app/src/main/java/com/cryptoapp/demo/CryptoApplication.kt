package com.cryptoapp.demo

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltAndroidApp
class CryptoApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}