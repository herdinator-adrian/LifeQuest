
package com.example.lifequest.Components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.launch
import java.time.ZoneId
import java.time.Instant
import com.example.lifequest.Data.database.AppDatabase
import com.example.lifequest.Data.model.TaskDifficulty
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import android.util.Log
import com.example.lifequest.Data.dao.DailyDao
import com.example.lifequest.Data.model.Daily
import com.example.lifequest.ViewModel.DayOfWeekViewModel
import java.time.DayOfWeek

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddDailyMenu(
    dailyDao: DailyDao,
    onBack: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    var selectedDays by remember { mutableStateOf(setOf(
        DayOfWeek.MONDAY,
        DayOfWeek.TUESDAY,
        DayOfWeek.WEDNESDAY,
        DayOfWeek.THURSDAY,
        DayOfWeek.FRIDAY
    )) }

    var error by remember { mutableStateOf("") }
    var saving by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    var difficulty by remember {
        mutableStateOf(TaskDifficulty.Trivial)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Text(
            text = "Nueva diaria",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(20.dp))

        NameDescriptionFields(
            name = name,
            onNameChange = { name = it },
            description = description,
            onDescriptionChange = { description = it }
        )

        Spacer(Modifier.height(20.dp))

        DaySelector(
            selectedDays = selectedDays,
            onSelectionChange = { selectedDays = it }
        )

        Spacer(Modifier.height(20.dp))

        // Opciones de dificultad
        DifficultySelector(
            difficulty = difficulty,
            onDifficultyChange = { difficulty = it }
        )

        Spacer(Modifier.height(12.dp))

        if (error.isNotEmpty()) {
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                fontSize = 14.sp
            )
        }

        // Empuja el botón hacia la parte inferior.
        Spacer(Modifier.weight(1f))

        Button(
            onClick = {
                if (name.isBlank()) {
                    error = "El nombre de la tarea es obligatorio."
                    return@Button
                }
                error = ""
                saving = true

                val daily = Daily(
                    name = name,
                    description = description,
                    createdAt = System.currentTimeMillis(),
                    repeatDays = DayOfWeekViewModel.encodeDays(selectedDays),
                    dailyDifficulty = difficulty.name
                )

                scope.launch {
                    try {
                        dailyDao.insertDaily(daily)
                        onBack()
                    } catch (e: Exception) {
                        error = "No se pudo guardar la tarea."
                    } finally {
                        saving = false
                    }
                }
            },
            enabled = !saving,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
        ) {
            Text(
                if (saving) "Guardando..."
                else "Añadir diaria"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddDailyMenuPreview(){
    val context = LocalContext.current
    val database = AppDatabase.getDatabase(context)

    AddDailyMenu(
        database.dailyDao(),
        {}
    )
}
