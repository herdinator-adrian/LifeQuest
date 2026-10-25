package com.example.lifequest.Data.model

import androidx.room3.Entity
import androidx.room3.PrimaryKey
import com.google.firebase.Timestamp
import java.math.BigInteger

@Entity(tableName = "dailies")
data class Daily(
    @PrimaryKey(autoGenerate = true)
    val dailyId: Int = 0,
    val name: String,
    val description: String,
    val createdAt: Long,
    val repeatDays: String,
    val dailyDifficulty: String,
    val lastCompletedDate: Long? = null
)