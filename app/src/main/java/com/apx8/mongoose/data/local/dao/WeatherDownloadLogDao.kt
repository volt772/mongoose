package com.apx8.mongoose.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.apx8.mongoose.data.local.entity.WeatherDownloadLog

@Dao
interface WeatherDownloadLogDao {

    // ✅ 특정 정시 기준 캐시 여부 확인
    @Query("""
        SELECT COUNT(*) FROM weather_download_log
        WHERE stadiumCode = :stadiumCode AND league = :league AND updatedAt = :updatedAt
    """)
    suspend fun isDownloadedExact(
        stadiumCode: String,
        league: String,
        updatedAt: Long
    ): Int

    // ✅ 캐시 삽입 (같은 stadium+league면 updatedAt 갱신)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(log: WeatherDownloadLog)

    // ✅ 캐시 전체 삭제 (테스트용 or 리셋용)
    @Query("DELETE FROM weather_download_log")
    suspend fun clearAll()
}
