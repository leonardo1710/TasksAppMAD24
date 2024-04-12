package com.example.tasksappmad24

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tasksappmad24.models.Task
import com.example.tasksappmad24.models.getTasks
import com.example.tasksappmad24.repositories.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TaskViewModel(private val repository: TaskRepository): ViewModel() {
    private val _tasks = MutableStateFlow(listOf<Task>())
    val tasks: StateFlow<List<Task>> = _tasks.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getAllTasks().collect {taskList ->
                _tasks.value = taskList
            }
        }
    }

    fun toggleDoneState(task: Task) {
        task.isDone = !task.isDone

        viewModelScope.launch {
            repository.update(task)
        }
    }

    fun addTask(task: Task){
        viewModelScope.launch {
            repository.add(task)
        }

    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            repository.delete(task)
        }
    }
}