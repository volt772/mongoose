package com.apx8.mongoose.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.apx8.mongoose.data.local.entity.ForecastWeather

@Dao
interface ForecastWeatherDao {

    // ✅ 기존 데이터 삭제 (stadiumCode + league 기준)
    @Query("""
        DELETE FROM forecast_weather
        WHERE stadiumCode = :stadiumCode AND league = :league
    """)
    suspend fun delete(stadiumCode: String, league: String)

    // ✅ 새 데이터 삽입
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(forecastWeather: ForecastWeather)

    // ✅ 삭제 + 삽입 트랜잭션
    @Transaction
    suspend fun replace(stadiumCode: String, league: String, newWeather: ForecastWeather) {
        delete(stadiumCode, league)
        insert(newWeather)
    }

    // ✅ 최신 데이터 조회 (updatedAt 기준 내림차순 1건)
    @Query("""
        SELECT * FROM forecast_weather
        WHERE stadiumCode = :stadiumCode
        ORDER BY updatedAt DESC
        LIMIT 1
    """)
    suspend fun getLatest(stadiumCode: String): ForecastWeather?
}
