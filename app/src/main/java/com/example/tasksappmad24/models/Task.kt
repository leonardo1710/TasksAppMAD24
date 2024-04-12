package com.example.tasksappmad24.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo
    val label: String,
    var isDone: Boolean = false
){
    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }
}

fun getTasks(): List<Task> {
    return listOf(
        Task(label = "read a book"),
        Task( label = "learn mad"),
        Task( label = "water the flowers")
    )
}
