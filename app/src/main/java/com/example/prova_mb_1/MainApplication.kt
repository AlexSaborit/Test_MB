package com.example.prova_mb_1

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

//this class is needed to make the app use Hilt
@HiltAndroidApp
class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}