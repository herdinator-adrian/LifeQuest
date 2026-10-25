package com.example.lifequest.Components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lifequest.Data.model.Task
import com.example.lifequest.Data.model.TaskDifficulty
import com.example.lifequest.ui.theme.ultra_l_red
import kotlinx.coroutines.delay
import java.lang.System.currentTimeMillis

@Composable
fun TaskCard(
    task: Task,
    onComplete: () -> Unit
) {
    var now by remember {
        mutableLongStateOf(currentTimeMillis())
    }

    LaunchedEffect(task.expiresAt) {
        while (true) {
            now = currentTimeMillis()
            delay(30_000L)
        }
    }

    val remaining = (task.expiresAt - now).coerceAtLeast(0L)
    val expired = now >= task.expiresAt

    val timeText = when {
        expired -> "Expirada"
        remaining >= 86_400_000L ->
            "${remaining / 86_400_000L} d " +
                    "${(remaining % 86_400_000L) / 3_600_000L} h"
        remaining >= 3_600_000L ->
            "${remaining / 3_600_000L} h " +
                    "${(remaining % 3_600_000L) / 60_000L} min"
        else -> "${remaining / 60_000L} min restantes"
    }

    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.elevatedCardColors(
            containerColor = if (expired) {
                ultra_l_red
            } else{
                MaterialTheme.colorScheme.surface
            }
        ),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 3.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,

            ) {
            // Botón para completar la tarea
            IconButton(
                onClick = onComplete,
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Completar tarea",
                    tint = Color(0xFF388E3C)
                )
            }

            spaceH(8)

            // Nombre y tiempo restante
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = task.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.AccessTime,
                        contentDescription = null,
                        modifier = Modifier.size(13.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    spaceH(4)

                    Text(
                        text = timeText,
                        fontSize = 11.sp,
                        color = if (remaining < 3_600_000L) {
                            Color(0xFFD32F2F)
                        } else {
                            MaterialTheme.colorScheme.onSurfaceVariant
                        },
                        maxLines = 1
                    )
                }
            }

            val difficulty = TaskDifficulty.entries.find {
                it.name.equals(task.taskDifficulty, ignoreCase = true) ||
                        it.label.equals(task.taskDifficulty, ignoreCase = true)
            } ?: TaskDifficulty.Medium

            DifficultyIcon(
                difficulty = difficulty,
                modifier = Modifier.size(35.dp)
            )
        }

    }
}

@Preview
@Composable
fun TaskCardViewPreview(){
    val duration = 24 * 60 * 60 * 1000L
    TaskCard(task = Task(
        0,
        "Workout Routine",
        "Complete the day's workout routine.",
        currentTimeMillis(),
        currentTimeMillis() + duration,
        TaskDifficulty.Epic.name),
        {})
}