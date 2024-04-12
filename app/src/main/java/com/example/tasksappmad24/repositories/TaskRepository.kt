package com.example.tasksappmad24.repositories

import com.example.tasksappmad24.database.TaskDao
import com.example.tasksappmad24.models.Task

class TaskRepository(private val taskDao: TaskDao) {

    suspend fun add(task: Task) = taskDao.add(task)

    suspend fun delete(task: Task) = taskDao.delete(task)

    suspend fun update(task: Task) = taskDao.update(task)

    fun getAllTasks() = taskDao.readAll()

    companion object {
        // For Singleton instantiation
        @Volatile private var instance: TaskRepository? = null

        fun getInstance(dao: TaskDao) =
            instance ?: synchronized(this) {
                instance ?: TaskRepository(dao).also { instance = it }
            }
    }
}