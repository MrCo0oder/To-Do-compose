package com.example.todo.ui.navigation.destinations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.todo.ui.navigation.Screen
import com.example.todo.ui.screens.SharedViewModel
import com.example.todo.util.Action

fun NavGraphBuilder.taskComposable(
    navigateToListScreen: (Action) -> Unit,
    sharedViewModel: SharedViewModel
) {
    composable<Screen.TaskScreen> {
    }
}