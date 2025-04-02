package com.example.todo.ui.screens.list

import android.annotation.SuppressLint
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todo.data.models.ToDoTask
import com.example.todo.ui.components.EmptyContent
import com.example.todo.ui.screens.SharedViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ListScreen(navigateToTaskScreen: (Int) -> Unit, sharedViewModel: SharedViewModel) {
    LaunchedEffect(key1 = true) {
        sharedViewModel.getAllTasks()
    }
    val tasks by sharedViewModel.allTasks.collectAsState()
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
        if (tasks.isEmpty())
            EmptyContent()
        else
            ListContent(
                tasks = tasks,
                navigateToTaskScreen = navigateToTaskScreen,
            )
    }
}

@Composable
fun ListContent(
    tasks: List<ToDoTask>,
    navigateToTaskScreen: (Int) -> Unit,
) {
    LazyColumn {
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
    ListScreen(navigateToTaskScreen = {}, sharedViewModel = viewModel())
}