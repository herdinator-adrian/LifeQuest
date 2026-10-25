package com.example.lifequest.Data.repository

import com.example.lifequest.Data.dao.DailyDao
import com.example.lifequest.Data.model.Daily
import com.example.lifequest.Data.model.Task
import kotlinx.coroutines.flow.Flow
import java.lang.System.currentTimeMillis
import java.time.LocalDate

class DailyRepository(private val dailyDao: DailyDao) {
    val allDailies: Flow<List<Daily>> = dailyDao.getAllDailies()

    suspend fun insertDaily(daily: Daily){
        dailyDao.insertDaily(daily)
    }

    suspend fun deleteDaily(dailyId: Int){
        dailyDao.getDailyById(dailyId)?.let{
            dailyDao.deleteDaily(it)
        }
    }

    suspend fun getDailyById(id:Int) = dailyDao.getDailyById(id)

    suspend fun getDailyCount()=dailyDao.getDailyCount()

    fun getActiveDailies(day: String): Flow<List<Daily>> = dailyDao.getActiveDailies(day, LocalDate.now().toEpochDay())

    suspend fun completeDaily(dailyId: Int) = dailyDao.completeDaily(dailyId, LocalDate.now().toEpochDay())
}