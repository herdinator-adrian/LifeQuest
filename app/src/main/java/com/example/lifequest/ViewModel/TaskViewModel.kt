@file:Suppress("PackageName", "unused")

package com.example.lifequest.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lifequest.Data.model.Product
import com.example.lifequest.Data.model.Task
import com.example.lifequest.Data.model.TaskDifficulty
import com.example.lifequest.Data.repository.ProductRepository
import com.example.lifequest.Data.repository.TaskRepository
import com.example.lifequest.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.lang.System.currentTimeMillis

class TaskViewModel(private val repository: TaskRepository) : ViewModel() {
    val products: StateFlow<List<Task>> = repository.allTasks
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    init {
        checkAndInsertInitialData()
    }

    fun addTask(task: Task){
        viewModelScope.launch(context = Dispatchers.IO) {
            repository.insertTask(task)
        }
    }

    fun removeTask(taskId: Int){
        viewModelScope.launch {
            repository.deleteTask(taskId)
        }
    }

    private fun checkAndInsertInitialData() {
        viewModelScope.launch {
            if (repository.getTaskCount() == 0) {
                val initialTasks = listOf(
                    Task(
                        0,
                        "Workout Routine",
                        "Do today's workout routine.",
                        currentTimeMillis(),
                        currentTimeMillis() + 24 * 60 * 60 * 1000L,
                        TaskDifficulty.Hard.name),
                    Task(
                        1,
                        "Read 30 pages",
                        "Read 30 pages of the current book you are reading.",
                        currentTimeMillis(),
                        currentTimeMillis() + 10 * 60 * 60 * 1000L,
                        TaskDifficulty.Medium.name),
                    Task(
                        2,
                        "Water plants",
                        "Water all the plants in the garden.",
                        currentTimeMillis(),
                        currentTimeMillis() + 15 * 60 * 60 * 1000L,
                        TaskDifficulty.Trivial.name),

                )
                initialTasks.forEach { repository.insertTask(it) }
            }
        }
    }
}
