package com.mygoals

import com.mygoals.ui.theme.TaskScreen

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels // <-- ВАЖНЫЙ ИМПОРТ
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mygoals.data.AppDatabase
import com.mygoals.data.TaskRepository
import com.mygoals.ui.theme.MyGoalsTheme
import com.mygoals.viewmodel.TaskViewModel

class MainActivity : ComponentActivity() {

    // Создаем ViewModel стандартным способом Android.
    // Она гарантированно будет привязана к жизненному циклу Activity.
    private val viewModel: TaskViewModel by viewModels {
        TaskViewModelFactory(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MyGoalsTheme { // <-- ВСТАВЬТЕ СЮДА ВАШЕ НАЗВАНИЕ ТЕМЫ
                Surface (
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
                ) {
                    // Просто передаем готовый viewModel в наш экран.
                    // Никаких viewModel() внутри Compose больше не нужно!
                    TaskScreen(viewModel = viewModel)
                }
            }
        }
    }
}

// Фабрика остается без изменений, она создает ViewModel с нашей базой данных
class TaskViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
            val database = AppDatabase.getDatabase(context)
            val repository = TaskRepository(database.taskDao())
            @Suppress("UNCHECKED_CAST")
            return TaskViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}