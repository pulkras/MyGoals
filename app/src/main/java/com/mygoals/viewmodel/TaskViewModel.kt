package com.mygoals.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mygoals.data.TaskEntity
import com.mygoals.data.TaskRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TaskViewModel(private val repository: TaskRepository) : ViewModel() {

    // StateFlow автоматически собирает данные из БД и хранит последнее состояние.
    // SharingStarted.Lazily означает, что сбор данных начнется только когда UI начнет их наблюдать.
    val tasks: StateFlow<List<TaskEntity>> = repository.allTasks
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = emptyList()
        )

    // Текст, который пользователь вводит в поле ввода
    var inputText: String = ""
        private set

    // Обновление текста при вводе
    fun onInputTextChanged(newText: String) {
        inputText = newText
    }

    // Добавление новой задачи
    fun addTask(title: String) {
        if (title.isBlank()) return

        val newTask = TaskEntity(title = title.trim(), isCompleted = false)

        viewModelScope.launch {
            repository.insertTask(newTask)
        }
    }

    // Удаление задачи
    fun deleteTask(task: TaskEntity) {
        viewModelScope.launch {
            repository.deleteTask(task)
        }
    }

    // Переключение статуса выполнения (галочка)
    fun toggleTaskCompletion(task: TaskEntity) {
        viewModelScope.launch {
            val updatedTask = task.copy(isCompleted = !task.isCompleted)
            repository.updateTask(updatedTask) // Room обновит запись, т.к. ID совпадет
        }
    }
}