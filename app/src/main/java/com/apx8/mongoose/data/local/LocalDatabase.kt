package com.apx8.mongoose.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.apx8.mongoose.data.local.dao.CurrentWeatherDao
import com.apx8.mongoose.data.local.dao.ForecastWeatherDao
import com.apx8.mongoose.data.local.dao.WeatherDownloadLogDao
import com.apx8.mongoose.data.local.entity.CurrentWeather
import com.apx8.mongoose.data.local.entity.ForecastWeather
import com.apx8.mongoose.data.local.entity.WeatherDownloadLog

@Database(
    entities = [
        WeatherDownloadLog::class,
        CurrentWeather::class,
        ForecastWeather::class
   ],
    version = 1,
    exportSchema = false
)

abstract class LocalDatabase : RoomDatabase() {
    abstract fun weatherDownloadLogDao(): WeatherDownloadLogDao
    abstract fun currentWeatherDao(): CurrentWeatherDao
    abstract fun forecastWeatherDao(): ForecastWeatherDao
}