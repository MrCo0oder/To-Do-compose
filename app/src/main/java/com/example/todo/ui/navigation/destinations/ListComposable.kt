package com.example.todo.ui.navigation.destinations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.todo.ui.navigation.Screen
import com.example.todo.ui.screens.list.ListScreen

fun NavGraphBuilder.listComposable(
    navigateToTaskScreen: (Int) -> Unit
) {
    composable<Screen.ListScreen> {
        ListScreen(navigateToTaskScreen = navigateToTaskScreen)
    }
}