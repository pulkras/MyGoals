package com.mygoals.data

import kotlinx.coroutines.flow.Flow

// Класс принимает TaskDao через конструктор (внедрение зависимостей)
class TaskRepository(private val taskDao: TaskDao) {

    // Просто передаем Flow из DAO наружу
    val allTasks: Flow<List<TaskEntity>> = taskDao.getAllTasks()

    // Функции для изменения данных (вызываются из ViewModel)
    suspend fun insertTask(task: TaskEntity) {
        taskDao.insertTask(task)
    }

    suspend fun updateTask(task: TaskEntity) {
        taskDao.updateTask(task)
    }
    suspend fun deleteTask(task: TaskEntity) {
        taskDao.deleteTask(task)
    }
}