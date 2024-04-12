package com.example.tasksappmad24.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.tasksappmad24.models.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Insert
    suspend fun add(task: Task)

    @Update
    suspend fun update(task: Task)

    @Delete
    suspend fun delete(task: Task)

    @Query("SELECT * from task")
    fun readAll(): Flow<List<Task>>

    @Query("SELECT * from task where isDone = 1")
    fun readAllChecked(): List<Task>

    @Query("SELECT * from task where id=:taskId")
    fun getTaskById(taskId: Int): Task

    @Query("DELETE from task")
    fun deleteAll()
}