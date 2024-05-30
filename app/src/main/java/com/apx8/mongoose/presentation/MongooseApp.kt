package com.apx8.mongoose.presentation

import android.app.Application
import android.content.Context
import com.apx8.mongoose.BuildConfig
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MongooseApp: Application() {

    override fun onCreate() {
        super.onCreate()

        appContext = applicationContext

        apiKey = BuildConfig.API_KEY
    }

    companion object {
        lateinit var appContext: Context
            private set

        lateinit var apiKey: String
            private set
    }
}