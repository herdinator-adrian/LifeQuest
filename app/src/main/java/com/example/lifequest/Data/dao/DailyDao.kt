package com.example.lifequest.Data.dao

import androidx.room3.Dao
import androidx.room3.Delete
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import com.example.lifequest.Data.model.CartItem
import com.example.lifequest.Data.model.Daily
import com.example.lifequest.Data.model.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface DailyDao {
    @Query("SELECT * FROM dailies")
    fun getAllDailies(): Flow<List<Daily>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDaily(daily: Daily)

    @Delete
    suspend fun deleteDaily(daily: Daily)

    @Query("SELECT * FROM dailies WHERE dailyId = :id")
    suspend fun getDailyById(id: Int): Daily?

    @Query("SELECT COUNT(*) FROM dailies")
    suspend fun getDailyCount(): Int

    @Query("""SELECT * FROM dailies 
        WHERE (',' || repeatDays || ',') LIKE '%,' || :day || ',%'
        AND (
            lastCompletedDate IS NULL
            OR lastCompletedDate != :currentDate
        )
    """)
    fun getActiveDailies(day: String, currentDate: Long): Flow<List<Daily>>

    @Query("""
    UPDATE dailies
    SET lastCompletedDate = :completedDate
    WHERE dailyId = :dailyId
""")
    suspend fun completeDaily(
        dailyId: Int,
        completedDate: Long
    )


}