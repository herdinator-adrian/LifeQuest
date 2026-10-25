package com.example.lifequest.Components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import com.example.lifequest.Data.model.TaskDifficulty
import java.time.DayOfWeek
import java.time.Instant
import java.time.ZoneId


@Composable
fun NameDescriptionFields(
    name: String,
    onNameChange: (String) -> Unit,
    description: String,
    onDescriptionChange: (String) -> Unit,
    namePlaceholder: String = "Ej. Estudiar matemáticas"
) {
    Column {
        OutlinedTextField(
            value = name,
            onValueChange = onNameChange,
            label = { Text("Nombre") },
            placeholder = { Text(namePlaceholder) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences
            ),
            modifier = Modifier.fillMaxWidth()
        )

        spaceV(12)

        OutlinedTextField(
            value = description,
            onValueChange = onDescriptionChange,
            label = { Text("Descripción (opcional)") },
            placeholder = { Text("Añade detalles...") },
            minLines = 2,
            maxLines = 4,
            modifier = Modifier.fillMaxWidth()
        )
    }
}


@Composable
fun DifficultySelector(
    difficulty: TaskDifficulty,
    onDifficultyChange: (TaskDifficulty) -> Unit
) {
    Column {
        Text(
            text = "Dificultad",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(Modifier.height(8.dp))

        Row(
            modifier = Modifier.horizontalScroll(
                rememberScrollState()
            ),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TaskDifficulty.entries.forEach { option ->
                val selected = difficulty == option

                FilterChip(
                    selected = selected,
                    onClick = { onDifficultyChange(option) },
                    label = { Text(option.label) },
                    leadingIcon = {
                        Icon(
                            imageVector = option.icon,
                            contentDescription = null,
                            tint = option.color
                        )
                    },
                    border = FilterChipDefaults.filterChipBorder(
                        enabled = true,
                        selected = selected,
                        borderColor = option.color,
                        selectedBorderColor = option.color
                    ),
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor =
                            option.color.copy(alpha = 0.18f),
                        selectedLabelColor = option.color,
                        iconColor = option.color,
                        selectedLeadingIconColor = option.color
                    )
                )
            }
        }
    }
}

@Composable
fun DaySelector(
    selectedDays: Set<DayOfWeek>,
    onSelectionChange: (Set<DayOfWeek>) -> Unit
) {
    val days = listOf(
        DayOfWeek.MONDAY to "Lun",
        DayOfWeek.TUESDAY to "Mar",
        DayOfWeek.WEDNESDAY to "Mié",
        DayOfWeek.THURSDAY to "Jue",
        DayOfWeek.FRIDAY to "Vie",
        DayOfWeek.SATURDAY to "Sáb",
        DayOfWeek.SUNDAY to "Dom"
    )

    Column {
        Text(
            text = "Días activos",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(Modifier.height(8.dp))

        Row(
            modifier = Modifier.horizontalScroll(
                rememberScrollState()
            ),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            days.forEach { (day, label) ->
                val selected = day in selectedDays

                FilterChip(
                    selected = selected,
                    onClick = {
                        val updatedDays = if (selected) {
                            selectedDays - day
                        } else {
                            selectedDays + day
                        }

                        onSelectionChange(updatedDays)
                    },
                    label = { Text(label) }
                )
            }
        }
    }
}
