package com.apx8.mongoose.data.remote

import retrofit2.http.GET
import retrofit2.http.Query


interface WeatherDirectApi {

    @GET("data/2.5/weather")
    suspend fun getCurrentWeatherData(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") appId: String,
        @Query("units") units: String?= "imperial",
        @Query("lang") lang: String?= "en",
    ): CurrentResponseDto

    @GET("data/2.5/forecast")
    suspend fun getForecastWeatherData(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") appId: String,
        @Query("units") units: String?= "imperial",
        @Query("lang") lang: String?= "en",
    ): ForecastResponseDto
}