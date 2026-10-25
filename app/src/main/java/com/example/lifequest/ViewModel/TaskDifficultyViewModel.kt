
package com.example.lifequest.Data.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.lifequest.ui.theme.easy_color
import com.example.lifequest.ui.theme.epic_color
import com.example.lifequest.ui.theme.hard_color
import com.example.lifequest.ui.theme.medium_color
import com.example.lifequest.ui.theme.trivial_color

enum class TaskDifficulty(
    val label: String,
    val icon: ImageVector,
    val color: Color
) {
    Trivial("Trivial", Icons.Default.Spa, trivial_color),
    Easy("Easy", Icons.Default.CheckCircle, easy_color),
    Medium("Medium", Icons.Default.Bolt, medium_color),
    Hard("Hard", Icons.Default.LocalFireDepartment, hard_color),
    Epic("Epic", Icons.Default.EmojiEvents, epic_color)
}

/*
class TaskDifficultyViewModel {
    companion object {
        fun GetColorByDifficulty(taskDifficulty: TaskDifficulty) : Color {
            when (taskDifficulty){
                TaskDifficulty.Trivial -> return trivial_color
                TaskDifficulty.Easy -> return easy_color
                TaskDifficulty.Medium -> return medium_color
                TaskDifficulty.Hard -> return hard_color
                TaskDifficulty.Epic -> return epic_color
                else -> return trivial_color
            }
        }

        fun GetColorByDifficulty(taskDifficulty: String) : Color {
            return GetColorByDifficulty(StringToTaskDifficulty(taskDifficulty))
        }

        fun GetIconByDifficulty(taskDifficulty: TaskDifficulty) : Int {
            when (taskDifficulty){
                TaskDifficulty.Trivial -> return R.drawable.outline_potted_plant_24
                TaskDifficulty.Easy -> return R.drawable.outline_wb_sunny_24
                TaskDifficulty.Medium -> return R.drawable.outline_local_fire_department_24
                TaskDifficulty.Hard -> return R.drawable.outline_swords_24
                TaskDifficulty.Epic -> return R.drawable.outline_crown_24
                else -> return R.drawable.outline_potted_plant_24
            }
        }

        fun GetIconByDifficulty(taskDifficulty: String) : Int {
            return GetIconByDifficulty(StringToTaskDifficulty(taskDifficulty))
        }

        fun TaskDifficultyToString(taskDifficulty: TaskDifficulty): String {
            return taskDifficulty.name
        }

        fun StringToTaskDifficulty(name: String) : TaskDifficulty {
            val a : TaskDifficulty = enumValueOf(name)
            return a
        }
    }
}*/