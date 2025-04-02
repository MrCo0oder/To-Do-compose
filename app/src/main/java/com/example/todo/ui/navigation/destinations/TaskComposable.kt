package com.example.todo.ui.navigation.destinations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.todo.ui.navigation.Screens.Companion.TASK_SCREEN
import com.example.todo.util.Action

fun NavGraphBuilder.taskComposable(
    navigateToListScreen: (Action) -> Unit
) {
    composable(
        route = TASK_SCREEN, arguments = listOf(navArgument(TASK_SCREEN) {
            type = NavType.IntType
        })
    ) {
    }
}