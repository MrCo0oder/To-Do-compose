package com.example.todo.ui.navigation.destinations

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.todo.ui.navigation.Screen
import com.example.todo.ui.screens.SharedViewModel
import com.example.todo.ui.screens.task.TaskScreen
import com.example.todo.util.Action

fun NavGraphBuilder.taskComposable(
    navigateToListScreen: (Action) -> Unit,
    sharedViewModel: SharedViewModel
) {
    composable<Screen.TaskScreen> {
        val taskId = it.toRoute<Screen.TaskScreen>().id ?: -1
        val selectedTaskState = sharedViewModel.selectedTask.collectAsState().value
        LaunchedEffect(key1 = taskId) {
//            if (taskId != -1)
                sharedViewModel.getSelectedTask(taskId)
        }
        TaskScreen(
            sharedViewModel = sharedViewModel,
            toDoTaskState = selectedTaskState,
            navigateToListScreen = navigateToListScreen
        )
    }
}