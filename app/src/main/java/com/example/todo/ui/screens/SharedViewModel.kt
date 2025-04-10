package com.example.todo.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.data.models.Priority
import com.example.todo.data.models.ToDoTask
import com.example.todo.data.repo.ToDoRepository
import com.example.todo.util.Action
import com.example.todo.util.RequestState
import com.example.todo.util.TopBarState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(private val repository: ToDoRepository) : ViewModel() {
    private val _allTasks = MutableStateFlow<RequestState<List<ToDoTask>>>(RequestState.Idle)
    val allTasks: StateFlow<RequestState<List<ToDoTask>>> = _allTasks

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
        _allTasks.value = RequestState.Loading
        try {
            viewModelScope.launch {
                repository.getAllTasks().collect { tasks ->
                    _allTasks.value = RequestState.Success(tasks)
                }
            }
        } catch (e: Exception) {
            _allTasks.value = RequestState.Error(e)
        }
    }

    private fun addTask(toDoTask: ToDoTask) {
        viewModelScope.launch {
            repository.insertTask(toDoTask)
        }
    }

    private fun updateTask(toDoTask: ToDoTask) {
        viewModelScope.launch {
            repository.updateTask(toDoTask)
        }
    }

    private fun deleteTask(toDoTask: ToDoTask) {
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
        _allTasks.value = RequestState.Loading
        viewModelScope.launch {
            repository.searchDatabase(searchQuery).collect { tasks ->
                _allTasks.value = RequestState.Success(tasks)
            }
        }
    }

    private val _task = MutableStateFlow<RequestState<ToDoTask?>>(RequestState.Idle)
    val selectedTask: StateFlow<RequestState<ToDoTask?>> = _task

    fun getSelectedTask(taskId: Int) {
        _task.value = RequestState.Loading
        try {
            viewModelScope.launch {
                repository.getSelectedTask(taskId).collect { tasks ->
                    _task.value = RequestState.Success(tasks)
                }
            }
        } catch (e: Exception) {
            _task.value = RequestState.Error(e)
        }
    }

    fun sortByLowPriority() {
        _allTasks.value = RequestState.Loading
        viewModelScope.launch {
            repository.sortByLowPriority().collect { tasks ->
                _allTasks.value = RequestState.Success(tasks)
            }
        }
    }

    fun sortByHighPriority() {
        _allTasks.value = RequestState.Loading
        viewModelScope.launch {
            repository.sortByHighPriority().collect { tasks ->
                _allTasks.value = RequestState.Success(tasks)
            }
        }
    }

    private val _selectedTask =
        MutableStateFlow<ToDoTask?>(ToDoTask(title = "", description = "", priority = Priority.LOW))
    val taskFlow: StateFlow<ToDoTask?> = _selectedTask

    fun setSelectedTask(toDoTask: ToDoTask?) {
        _selectedTask.value = toDoTask
    }

    fun onEvent(event: TaskScreenEvent) {
        when (event) {
            is TaskScreenEvent.SetTitle -> {
                _selectedTask.value =
                    _selectedTask.value?.copy(title = event.title)
            }

            is TaskScreenEvent.SetDescription -> {
                _selectedTask.update {
                    _selectedTask.value?.copy(description = event.description)
                }
            }

            is TaskScreenEvent.SetPriority -> {
                _selectedTask.update {
                    _selectedTask.value?.copy(priority = event.priority)
                }
            }
        }
    }

    fun onAction(action: Action) {
        when (action) {
            Action.NoAction -> {
                resetTask()
            }

            Action.Add -> {
                _selectedTask.value?.let {
                    addTask(it)
                }
                resetTask()
            }

            Action.Delete -> {
                _selectedTask.value?.let {
                    deleteTask(it)
                }
                resetTask()
            }

            Action.Update -> {
                _selectedTask.value?.let {
                    updateTask(it)
                }
                resetTask()
            }

            Action.DeleteAll -> {
                deleteAllTasks()
            }
        }
    }

    private fun resetTask() {
        _selectedTask.value =
            ToDoTask(title = "", description = "", priority = Priority.LOW)
    }
}

sealed class TaskScreenEvent {
    data class SetTitle(val title: String) : TaskScreenEvent()
    data class SetDescription(val description: String) : TaskScreenEvent()
    data class SetPriority(val priority: Priority) : TaskScreenEvent()
}