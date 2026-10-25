package com.example.lifequest.Data.model

import androidx.room3.Entity
import androidx.room3.PrimaryKey
import com.google.firebase.Timestamp
import java.math.BigInteger

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val taskId: Int = 0,
    val name: String,
    val description: String,
    val createdAt: Long,
    val expiresAt: Long,
    val taskDifficulty: String
)