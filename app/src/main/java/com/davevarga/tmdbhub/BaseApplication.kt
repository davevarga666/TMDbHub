package com.davevarga.tmdbhub

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApplication : Application() {

    companion object {
        var detailViewOpen = false
    }
}