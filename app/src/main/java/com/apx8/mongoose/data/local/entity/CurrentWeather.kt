package com.apx8.mongoose.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(
    tableName = "current_weather",
    indices = [Index(value = ["stadiumCode", "updatedAt", "league"], unique = true)]
)
data class CurrentWeather(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val stadiumCode: String,
    val league: String,
    val weatherJson: String,
    val updatedAt: Long
)


