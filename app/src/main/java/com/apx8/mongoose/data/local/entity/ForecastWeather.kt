package com.apx8.mongoose.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(
    tableName = "forecast_weather",
    indices = [Index(value = ["stadiumCode", "updatedAt", "league"], unique = true)]
)
data class ForecastWeather(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val stadiumCode: String,
    val league: String,
    val weatherJson: String,
    val updatedAt: Long
)
