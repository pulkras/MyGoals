package com.mygoals.ui.theme

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mygoals.viewmodel.TaskViewModel // ЗАМЕНИТЕ на ваш реальный пакет

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskScreen(viewModel: TaskViewModel) {
    // Собираем поток задач из ViewModel в состояние Compose
    val tasks by viewModel.tasks.collectAsStateWithLifecycle()

    // Локальное состояние для поля ввода
    var inputText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // ✨ ДОБАВЛЕНО: Отступ сверху, чтобы поле ввода не прилипало к статус-бару
        Spacer(modifier = Modifier.height(56.dp))

        // Верхняя панель с полем ввода и кнопкой
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = inputText,
                onValueChange = { inputText = it },
                modifier = Modifier.weight(1f),
                label = { Text("Новая задача") },
                singleLine = true,
                shape = MaterialTheme.shapes.medium // Скругленные углы
            )

            Spacer(modifier = Modifier.width(12.dp))

            Button(
                onClick = {
                    if (inputText.isNotBlank()) {
                        viewModel.addTask(inputText)
                        inputText = "" // Очищаем поле после добавления
                    }
                },
                enabled = inputText.isNotBlank(),
                modifier = Modifier.height(56.dp) // Высота как у TextField
            ) {
                Text("Добавить")
            }
        }

        Spacer(modifier = Modifier.height(24.dp)) // Отступ между вводом и списком

        // Список задач или пустое состояние
        if (tasks.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Список задач пуст.\nДобавьте первую!",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    // Выравнивание текста по центру
                    modifier = Modifier.padding(16.dp)
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp) // Расстояние между карточками
            ) {
                items(tasks, key = { it.id }) { task ->
                    TaskItem(
                        task = task,
                        onToggleCompletion = { viewModel.toggleTaskCompletion(task) },
                        onDelete = { viewModel.deleteTask(task) }
                    )
                }
            }
        }
    }
}