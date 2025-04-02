package com.example.todo.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.data.models.ToDoTask
import com.example.todo.data.repo.ToDoRepository
import com.example.todo.util.TopBarState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow

import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(private val repository: ToDoRepository) : ViewModel() {
    private val _allTasks = MutableStateFlow<List<ToDoTask>>(emptyList())
    val allTasks: StateFlow<List<ToDoTask>> = _allTasks

    private val searchAppBarState = MutableStateFlow(TopBarState.CLOSED)
    val searchAppBarStateFlow: StateFlow<TopBarState> = searchAppBarState

    private val searchText = MutableStateFlow("")
    val searchTextFlow: StateFlow<String> = searchText


    fun updateSearchText(query: String) {
        searchText.value = query
    }

    fun updateSearchAppBarState(topBarState: TopBarState) {
        searchAppBarState.value = topBarState
    }


    fun getAllTasks() {
        viewModelScope.launch {
            repository.getAllTasks().collect { tasks ->
                _allTasks.value = tasks
            }
        }
    }

    fun addTask(toDoTask: ToDoTask) {
        viewModelScope.launch {
            repository.insertTask(toDoTask)
        }
    }

    fun updateTask(toDoTask: ToDoTask) {
        viewModelScope.launch {
            repository.updateTask(toDoTask)
        }
    }

    fun deleteTask(toDoTask: ToDoTask) {
        viewModelScope.launch {
            repository.deleteTask(toDoTask)
        }
    }

    fun deleteAllTasks() {
        viewModelScope.launch {
            repository.deleteAllTasks()
        }
    }

    fun searchDatabase(searchQuery: String) {
        viewModelScope.launch {
            repository.searchDatabase(searchQuery).collect { tasks ->
                _allTasks.value = tasks
            }
        }
    }

    fun getSelectedTask(taskId: Int): StateFlow<ToDoTask?> {
        val task = MutableStateFlow<ToDoTask?>(null)
        viewModelScope.launch {
            repository.getSelectedTask(taskId).collect { tasks ->
                task.value = tasks
            }
        }
        return task
    }

    fun sortByLowPriority() {
        viewModelScope.launch {
            repository.sortByLowPriority().collect { tasks ->
                _allTasks.value = tasks
            }
        }
    }

    fun sortByHighPriority() {
        viewModelScope.launch {
            repository.sortByHighPriority().collect { tasks ->
                _allTasks.value = tasks
            }
        }
    }
}