package com.example.lifequest.Data.repository

import com.example.lifequest.Data.dao.TaskDao
import com.example.lifequest.Data.model.Task
import kotlinx.coroutines.flow.Flow

class TaskRepository(private val taskDao: TaskDao) {
    val allTasks: Flow<List<Task>> = taskDao.getAllTasks()

    suspend fun insertTask(task: Task){
        taskDao.insertTask(task)
    }

    suspend fun deleteTask(taskId: Int){
        taskDao.getTaskById(taskId)?.let{
            taskDao.deleteTask(it)
        }
    }

    suspend fun getTaskById(id:Int) = taskDao.getTaskById(id)

    suspend fun getTaskCount()=taskDao.getTaskCount()

    fun getActiveTasks(now: Long): Flow<List<Task>> = taskDao.getActiveTasks(now)
}