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

    private val _selectedTask = MutableStateFlow<ToDoTask?>(null)
    val taskFlow: StateFlow<ToDoTask?> = _selectedTask

    fun setSelectedTask(toDoTask: ToDoTask?) {
        _selectedTask.value = toDoTask
    }

    fun onEvent(event: TaskScreenEvent) {
        when (event) {
            is TaskScreenEvent.SetTitle -> {
                _selectedTask.update {
                    _selectedTask.value?.copy(title = event.title)
                }
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

            TaskScreenEvent.SaveTask -> {
                _selectedTask.value?.let {
                    viewModelScope.launch {
                        repository.updateTask(it)
                    }
                }
            }

            TaskScreenEvent.DeleteTask -> {
                _selectedTask.value?.let {
                    viewModelScope.launch {
                        repository.deleteTask(it)
                    }
                }
            }
        }
    }

    fun onAction(action: Action) {
        when (action) {
            Action.NoAction -> {
                _selectedTask.value = null
            }

            Action.Add -> {
                _selectedTask.value?.let {
                    viewModelScope.launch {
                        repository.insertTask(it)
                    }
                }
                _selectedTask.value = null
            }

            Action.Delete -> {
                _selectedTask.value?.let {
                    viewModelScope.launch {
                        repository.deleteTask(it)
                    }
                }
            }

            Action.Update -> {
                _selectedTask.value?.let {
                    viewModelScope.launch {
                        repository.updateTask(it)
                    }
                }
                _selectedTask.value = null
            }

            Action.DeleteAll -> {
                viewModelScope.launch {
                    repository.deleteAllTasks()
                }
                _selectedTask.value = null
            }

            Action.Undo -> {
                _selectedTask.value?.let {
                    viewModelScope.launch {
                        repository.insertTask(it)
                    }
                }
            }
        }
    }
}

sealed class TaskScreenEvent {
    data class SetTitle(val title: String) : TaskScreenEvent()
    data class SetDescription(val description: String) : TaskScreenEvent()
    data class SetPriority(val priority: Priority) : TaskScreenEvent()
    object SaveTask : TaskScreenEvent()
    object DeleteTask : TaskScreenEvent()

}