package com.apx8.mongoose.data.remote

import retrofit2.http.GET
import retrofit2.http.Query


interface WeatherApi {

    @GET("api/weather")
    suspend fun getAllWeatherData(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("stadium_code") stadiumCode: String,
        @Query("units") units: String?= "metric",
        @Query("lang") lang: String?= "kr",
        @Query("league") league: String?= "kbo",
    ): AllWeatherResponseDto
}