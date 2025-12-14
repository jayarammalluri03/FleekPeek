package com.example.fleekpeek

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class FPApplication() : Application(){


    override fun onCreate() {
        super.onCreate()
    }
}
