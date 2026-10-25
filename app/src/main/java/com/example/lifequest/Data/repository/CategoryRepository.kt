package com.example.lifequest.Data.repository

import com.example.lifequest.Data.dao.CategoryDao
import com.example.lifequest.Data.model.Category

class CategoryRepository(private val categoryDao: CategoryDao){
    suspend fun getCategories(): List<Category>{
        return categoryDao.getCategories()
    }
    suspend fun insertCategories(categories: List<Category>){
        categoryDao.insertCategories(categories)
    }
    suspend fun countCategories(): Int{
        return categoryDao.countCategories()
    }
}