package com.mygoals.ui.theme

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.mygoals.data.TaskEntity

@Composable
fun TaskItem(
    task: TaskEntity,
    onToggleCompletion: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Чекбокс для отметки выполнения
            Checkbox(
                checked = task.isCompleted,
                onCheckedChange = { onToggleCompletion() }
            )

            // Текст задачи
            Text(
                text = task.title,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
                    .clickable { onToggleCompletion() }, // Можно кликнуть и по тексту
                style = MaterialTheme.typography.bodyLarge,
                textDecoration = if (task.isCompleted) TextDecoration.LineThrough else TextDecoration.None,
                color = if (task.isCompleted) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onSurface
            )

            // Кнопка удаления
            IconButton(onClick = { onDelete() }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Удалить задачу",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}