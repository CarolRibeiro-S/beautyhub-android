package com.beautyhub.app

import android.app.Application

class BeautyHubApp : Application() {
    override fun onCreate() {
        super.onCreate()
        SessionManager.init(this)
    }
}