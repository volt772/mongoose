package com.apx8.mongoose.di

import com.apx8.mongoose.data.remote.WeatherApi
import com.apx8.mongoose.data.remote.WeatherDirectApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

//    private const val baseUrl = "https://api.openweathermap.org/"
//    private const val baseUrl = "http://192.168.45.250:5532/"
//    private const val baseUrl = "http://192.168.45.250/weather-api/"
    private const val baseUrl = "http://volt773.vps.phps.kr/weather-api/"
    private const val directUrl = "https://api.openweathermap.org/"


    @Provides
    @Singleton
    fun provideWeatherApi(
        @MongooseHttpClient okHttpClient: OkHttpClient,
    ): WeatherApi {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideDirectWeatherApi(
        @MongooseHttpClient okHttpClient: OkHttpClient,
    ): WeatherDirectApi {
        return Retrofit.Builder()
            .baseUrl(directUrl)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Singleton
    @MongooseHttpClient
    fun providePlanetHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder().apply {
            retryOnConnectionFailure(true)
            addInterceptor(loggingInterceptor)
        }.build()
    }
}
