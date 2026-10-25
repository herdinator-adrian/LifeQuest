package com.example.lifequest.Data.database

import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.room3.ColumnTypeConverter
import androidx.room3.ColumnTypeConverters
import androidx.room3.Database
import androidx.room3.Room
import androidx.room3.RoomDatabase
import com.example.lifequest.Data.dao.CartDao
import com.example.lifequest.Data.dao.CategoryDao
import com.example.lifequest.Data.dao.DailyDao
import com.example.lifequest.Data.dao.ProductDao
import com.example.lifequest.Data.dao.TaskDao
import com.example.lifequest.Data.model.CartItem
import com.example.lifequest.Data.model.Category
import com.example.lifequest.Data.model.Daily
import com.example.lifequest.Data.model.Product
import com.example.lifequest.Data.model.Task

object ColorConverter {
    @ColumnTypeConverter
    fun fromColor(color: Color): Int {
        return color.toArgb()
    }

    @ColumnTypeConverter
    fun toColor(colorInt: Int): Color {
        return Color(colorInt)
    }
}

@Database(
    entities = [
        Category::class,
        Product::class,
        CartItem::class,
        Task::class,
        Daily::class,
    ],
    version = 6,
    exportSchema = false
)
@ColumnTypeConverters(ColorConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun productDao(): ProductDao
    abstract fun cartItemDao(): CartDao

    abstract fun taskDao(): TaskDao

    abstract fun dailyDao(): DailyDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                    .fallbackToDestructiveMigration(true)
                    .build()
                    .also {
                        INSTANCE = it
                    }
            }
        }
    }
}
