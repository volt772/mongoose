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
        apiKey = API_KEY

        /* Key : AdMob Key*/
        adMobKey = if (BuildConfig.BUILD_TYPE == "debug") {
            ADMOB_DEBUG_KEY
        } else {
            ADMOB_RELEASE_KEY
        }
    }

    companion object {
        lateinit var appContext: Context
            private set

        lateinit var apiKey: String
            private set

        lateinit var adMobKey: String
            private set

        const val API_KEY="0db9893c91f62ebc471d8690237683e8"
        const val ADMOB_DEBUG_KEY="ca-app-pub-3940256099942544/9214589741"
        const val ADMOB_RELEASE_KEY="ca-app-pub-3086701116400661/5084268035"
    }
}