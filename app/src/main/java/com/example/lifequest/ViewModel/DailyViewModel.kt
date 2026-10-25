@file:Suppress("PackageName", "unused")

package com.example.lifequest.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lifequest.Data.model.Daily
import com.example.lifequest.Data.model.Product
import com.example.lifequest.Data.model.Task
import com.example.lifequest.Data.model.TaskDifficulty
import com.example.lifequest.Data.repository.DailyRepository
import com.example.lifequest.Data.repository.ProductRepository
import com.example.lifequest.Data.repository.TaskRepository
import com.example.lifequest.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.lang.System.currentTimeMillis
import java.time.LocalDate

class DailyViewModel(private val repository: DailyRepository) : ViewModel() {
    val products: StateFlow<List<Daily>> = repository.allDailies
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    init {
        checkAndInsertInitialData()
    }

    fun addDaily(daily: Daily){
        viewModelScope.launch(context = Dispatchers.IO) {
            repository.insertDaily(daily)
        }
    }

    fun completeDaily(dailyId: Int) {
        viewModelScope.launch {
            repository.completeDaily(dailyId = dailyId)
        }
    }

    fun removeDaily(dailyId: Int){
        viewModelScope.launch {
            repository.deleteDaily(dailyId)
        }
    }

    private fun checkAndInsertInitialData() {
        viewModelScope.launch {
            if (repository.getDailyCount() == 0) {
                val initialTasks = listOf(
                    Daily(
                        name="Ir a la escuela",
                        description = "",
                        createdAt = currentTimeMillis(),
                        repeatDays = "MONDAY,TUESDAY,WEDNESDAY,THURSDAY,FRIDAY",
                        dailyDifficulty = "Medium"
                    )

                )
                initialTasks.forEach { repository.insertDaily(it) }
            }
        }
    }
}
