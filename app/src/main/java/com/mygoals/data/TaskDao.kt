package com.mygoals.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    // Получаем список задач. Flow автоматически уведомит UI, когда данные в БД изменятся!
    @Query("SELECT * FROM tasks ORDER BY id DESC")
    fun getAllTasks(): Flow<List<TaskEntity>>

    // Добавляем новую задачу
    @Insert
    suspend fun insertTask(task: TaskEntity)

    @Update
    suspend fun updateTask(task: TaskEntity)

    // Удаляем задачу
    @Delete
    suspend fun deleteTask(task: TaskEntity)
}