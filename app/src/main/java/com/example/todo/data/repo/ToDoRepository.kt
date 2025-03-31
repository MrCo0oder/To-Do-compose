package com.example.todo.data.repo

import com.example.todo.data.ToDoDao
import com.example.todo.data.models.ToDoTask
import javax.inject.Inject

class ToDoRepository @Inject constructor(private val toDoDao: ToDoDao) {
    suspend fun insertTask(toDoTask: ToDoTask) = toDoDao.addTask(toDoTask)
    suspend fun updateTask(toDoTask: ToDoTask) = toDoDao.updateTask(toDoTask)
    suspend fun deleteTask(taskId: Int) = toDoDao.deleteTask(taskId)
    suspend fun deleteAllTasks() = toDoDao.deleteAllTasks()
    fun searchDatabase(searchQuery: String) = toDoDao.searchDatabase(searchQuery)
    fun sortByLowPriority() = toDoDao.sortByLowPriority()
    fun sortByHighPriority() = toDoDao.sortByHighPriority()
    fun getAllTasks() = toDoDao.getAllTasks()
    fun getSelectedTask(taskId: Int) = toDoDao.getSelectedTask(taskId)
}