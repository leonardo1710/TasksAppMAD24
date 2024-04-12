package com.example.tasksappmad24.di

import android.content.Context
import com.example.tasksappmad24.TaskViewModelFactory
import com.example.tasksappmad24.database.TaskDatabase
import com.example.tasksappmad24.repositories.TaskRepository

object InjectorUtils {
    private fun getTaskRepository(context: Context): TaskRepository {
        return TaskRepository.getInstance(TaskDatabase.getDatabase(context.applicationContext).taskDao())
    }

    fun provideTaskViewModelFactory(context: Context): TaskViewModelFactory{
        val repository = getTaskRepository(context)
        return TaskViewModelFactory(repository)
    }
}