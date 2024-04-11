package com.apx8.mongoose

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MongooseApp: Application() {

    override fun onCreate() {
        super.onCreate()

        apiKey = BuildConfig.API_KEY
    }

    companion object {
        lateinit var apiKey: String
            private set
    }
}