package com.apx8.mongoose.presentation

import android.app.Application
import android.content.Context
import com.apx8.mongoose.BuildConfig
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MongooseApp: Application() {

    override fun onCreate() {
        super.onCreate()

        /* App Context*/
        appContext = applicationContext

        /* Key : WeatherAPI Key*/
        apiKey = BuildConfig.API_KEY

        /* Key : AdMob Key*/
        adMobKey = if (BuildConfig.BUILD_TYPE == "debug") {
            BuildConfig.ADMOB_DEBUG_KEY
        } else {
            BuildConfig.ADMOB_RELEASE_KEY
        }

        /* Checker : Is Debugging App*/
        isDebugging = if (BuildConfig.BUILD_TYPE == "debug") {
            "Y"
        } else {
            "N"
        }
    }

    companion object {
        lateinit var appContext: Context
            private set

        lateinit var apiKey: String
            private set

        lateinit var adMobKey: String
            private set

        lateinit var isDebugging: String
            private set
    }
}