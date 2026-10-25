
package com.example.lifequest.Components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.ZoneId
import java.time.Instant
import java.time.format.DateTimeFormatter
import com.example.lifequest.Data.dao.TaskDao
import com.example.lifequest.Data.database.AppDatabase
import com.example.lifequest.Data.model.Task
import com.example.lifequest.Data.model.TaskDifficulty
import com.example.lifequest.Data.repository.TaskRepository
import com.example.lifequest.View.HomeView
import com.example.lifequest.ViewModel.TaskViewModel
import com.example.lifequest.ViewModel.TaskViewModelFactory
import com.example.lifequest.ui.theme.AplicacionTallerTheme
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.ui.unit.dp
import android.util.Log

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskMenu(
    taskDao: TaskDao,
    onBack: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    // Fecha inicial: hoy.
    var selectedDate by remember {
        mutableStateOf(LocalDate.now())
    }

    // Hora inicial: 23:59.
    var selectedHour by remember { mutableIntStateOf(23) }
    var selectedMinute by remember { mutableIntStateOf(59) }


    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }
    LaunchedEffect(showDatePicker, showTimePicker) {
        Log.d(
            "AddTaskMenu",
            "Fecha: $showDatePicker | Hora: $showTimePicker"
        )
    }

    var error by remember { mutableStateOf("") }
    var saving by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    val dateFormatter = remember {
        DateTimeFormatter.ofPattern("dd/MM/yyyy")
    }

    val timeFormatter = remember {
        DateTimeFormatter.ofPattern("HH:mm")
    }

    var difficulty by remember {
        mutableStateOf(TaskDifficulty.Trivial)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Text(
            text = "Nueva tarea",
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

        Text(
            text = "Fecha límite",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(Modifier.height(8.dp))

        // Selector de fecha y hora
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedButton(
                onClick = { showDatePicker = true },
                modifier = Modifier.weight(1f)
            ) {
                Text(selectedDate.format(dateFormatter))
            }

            OutlinedButton(
                onClick = { showTimePicker = true },
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    "%02d:%02d".format(
                        selectedHour, selectedMinute
                    )
                )
            }
        }

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

                val expiresAt = selectedDate
                    .atTime(selectedHour, selectedMinute)
                    .atZone(ZoneId.systemDefault())
                    .toInstant()
                    .toEpochMilli()

                if (expiresAt <= System.currentTimeMillis()) {
                    error = "La fecha límite debe estar en el futuro."
                    return@Button
                }

                error = ""
                saving = true

                val task = Task(
                    name = name.trim(),
                    description = description.trim(),
                    createdAt = System.currentTimeMillis(),
                    expiresAt = expiresAt,
                    taskDifficulty = difficulty.label
                )

                scope.launch {
                    try {
                        taskDao.insertTask(task)
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
                else "Añadir tarea"
            )
        }
    }

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = selectedDate
            .atStartOfDay(ZoneId.of("UTC"))
            .toInstant()
            .toEpochMilli()
    )

    val timePickerState = rememberTimePickerState(
        initialHour = selectedHour,
        initialMinute = selectedMinute,
        is24Hour = true
    )

    if (showDatePicker) {
        Log.d("AddTaskMenu", "Mostrando DatePickerDialog")
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            selectedDate = Instant.ofEpochMilli(millis)
                                .atZone(ZoneId.of("UTC"))
                                .toLocalDate()
                        }
                        showDatePicker = false
                    }
                ) {
                    Text("Aceptar")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDatePicker = false }
                ) {
                    Text("Cancelar")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    if (showTimePicker) {
        AlertDialog(
            onDismissRequest = { showTimePicker = false },
            title = { Text("Elegir hora límite") },
            text = {
                TimePicker(state = timePickerState)
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        Log.d("AddTaskMenu", "Botón de hora pulsado")
                        selectedHour = timePickerState.hour
                        selectedMinute = timePickerState.minute
                        showTimePicker = false
                    }
                ) {
                    Text("Aceptar")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showTimePicker = false }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AddTaskMenuPreview(){
    val context = LocalContext.current
    val database = AppDatabase.getDatabase(context)

    AddTaskMenu(
        database.taskDao(),
        {}
    )
}
