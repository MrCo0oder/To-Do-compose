package com.example.todo.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.util.Constants.TODO_TABLE

@Entity(tableName =TODO_TABLE)
data class ToDoTask(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String,
    val priority: Priority,
)
