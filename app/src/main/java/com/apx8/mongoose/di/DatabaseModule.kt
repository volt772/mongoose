package com.apx8.mongoose.di

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.apx8.mongoose.data.local.LocalDatabase
import com.apx8.mongoose.data.local.dao.CurrentWeatherDao
import com.apx8.mongoose.data.local.dao.ForecastWeatherDao
import com.apx8.mongoose.data.local.dao.WeatherDownloadLogDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext appContext: Context,
    ): LocalDatabase =
        Room.databaseBuilder(
            appContext,
            LocalDatabase::class.java,
            "ballpark_weather.db"
        )
        .addMigrations(MIGRATION_1_TO_2)
        .build()

    @Provides
    @Singleton
    fun provideWeatherDownloadLogDao(database: LocalDatabase): WeatherDownloadLogDao = database.weatherDownloadLogDao()

    @Provides
    @Singleton
    fun provideCurrentWeatherDao(database: LocalDatabase): CurrentWeatherDao = database.currentWeatherDao()

    @Provides
    @Singleton
    fun provideForecastWeatherDao(database: LocalDatabase): ForecastWeatherDao = database.forecastWeatherDao()
}

val MIGRATION_1_TO_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
//        database.execSQL("ALTER TABLE record ADD COLUMN memo TEXT DEFAULT '' NOT NULL")
    }
}