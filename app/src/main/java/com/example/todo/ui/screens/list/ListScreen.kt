package com.example.todo.ui.screens.list

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.todo.data.models.ToDoTask
import com.example.todo.ui.components.EmptyContent
import com.example.todo.ui.components.ErrorContent
import com.example.todo.ui.components.LoadingContent
import com.example.todo.ui.screens.SharedViewModel
import com.example.todo.util.RequestState
import com.example.todo.util.TopBarState

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ListScreen(
    navigateToTaskScreen: (Int) -> Unit,
    sharedViewModel: SharedViewModel,
) {
    LaunchedEffect(key1 = true) {
        sharedViewModel.getAllTasks()
    }
    val tasks by sharedViewModel.allTasks.collectAsState()
    val searchTasks by sharedViewModel.searchTasks.collectAsState()
    Scaffold(
        floatingActionButton = {
            TaskFloatingActionButton(navigateToTaskScreen)
        },
        topBar = {
            ListAppBar(
                sharedViewModel = sharedViewModel,
                topBarState = sharedViewModel.searchAppBarStateFlow.collectAsState().value,
                searchTextState = sharedViewModel.searchTextFlow.collectAsState().value
            )
        },
    ) {
        if (sharedViewModel.searchAppBarStateFlow.collectAsState().value == TopBarState.TRIGGERED)
            HandleSearchTasks(
                searchTasks = searchTasks,
                navigateToTaskScreen = navigateToTaskScreen,
                paddingValues = it
            )
        else
            HandleRegularList(tasks, it, navigateToTaskScreen, sharedViewModel)
    }
}

@Composable
private fun HandleRegularList(
    tasks: RequestState<List<ToDoTask>>,
    it: PaddingValues,
    navigateToTaskScreen: (Int) -> Unit,
    sharedViewModel: SharedViewModel
) {
    when (tasks) {
        is RequestState.Success -> {
            if (tasks.data.isEmpty())
                EmptyContent()
            else {
                Log.d(
                    "ListScreen: ",
                    tasks.data.toString()
                )
                ListContent(
                    paddingValues = it,
                    tasks = tasks.data,
                    navigateToTaskScreen = navigateToTaskScreen,
                )
            }
        }

        is RequestState.Error -> {
            ErrorContent(message = tasks.exception.message.toString()) {
                sharedViewModel.getAllTasks()
            }
        }

        else -> {
            LoadingContent()
        }
    }
}

@Composable
fun HandleSearchTasks(
    searchTasks: RequestState<List<ToDoTask>>,
    navigateToTaskScreen: (Int) -> Unit,
    paddingValues: PaddingValues,
) {
    when (searchTasks) {
        is RequestState.Success -> {
            if (searchTasks.data.isEmpty())
                EmptyContent()
            else
                ListContent(
                    paddingValues = paddingValues,
                    tasks = searchTasks.data,
                    navigateToTaskScreen = navigateToTaskScreen,
                )
        }

        is RequestState.Error -> {
            ErrorContent(message = searchTasks.exception.message.toString()) {
            }
        }

        else -> {
            LoadingContent()
        }
    }
}

@Composable
fun ListContent(
    tasks: List<ToDoTask>,
    navigateToTaskScreen: (Int) -> Unit,
    paddingValues: PaddingValues,
) {
    LazyColumn(
        modifier = Modifier.padding(
            top = paddingValues.calculateTopPadding(),
            bottom = paddingValues.calculateBottomPadding()
        )
    ) {
        items(tasks.size, key = { i -> tasks[i].id }) { i ->
            TaskItem(
                task = tasks[i],
                navigateToTaskScreen = navigateToTaskScreen,
            )
        }
    }
}

@Composable
@Preview
fun ListScreenPreview() {
//    ListScreen(navigateToTaskScreen = {}, sharedViewModel = viewModel(), action = Action.NoAction)
}