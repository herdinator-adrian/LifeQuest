package com.example.lifequest.Data.dao

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.Query
import com.example.lifequest.Data.model.Category

@Dao
interface CategoryDao {
    @Query("SELECT * FROM categories")
    suspend fun getCategories(): List<Category>
    @Query("SELECT COUNT (*) FROM categories")
    suspend fun countCategories(): Int
    @Insert
    suspend fun insertCategories(categories: List<Category>)
}