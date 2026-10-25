package com.example.lifequest.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.lifequest.Data.repository.DailyRepository
import com.example.lifequest.Data.repository.ProductRepository
import com.example.lifequest.Data.repository.TaskRepository

class DailyViewModelFactory (
    private val repository: DailyRepository
) : ViewModelProvider.Factory {
    override fun <T: ViewModel> create(modelClass: Class<T>): T{
        if (modelClass.isAssignableFrom(DailyViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return DailyViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }
}