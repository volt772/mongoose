package com.apx8.mongoose.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(
    tableName = "weather_download_log",
    indices = [Index(value = ["stadiumCode", "league"], unique = true)]
)
data class WeatherDownloadLog(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val league: String,
    val stadiumCode: String,
    val updatedAt: Long
)
