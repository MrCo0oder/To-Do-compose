package com.example.todo.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.todo.ui.navigation.Screens.Companion.LIST_SCREEN
import com.example.todo.ui.navigation.destinations.listComposable
import com.example.todo.ui.navigation.destinations.taskComposable

@Composable
fun AppNavigation(
    navHostController: NavHostController,

    ) {
    val screen = remember(navHostController) {
        Screens(navHostController)
    }
    NavHost(navController = navHostController, startDestination = LIST_SCREEN) {
        listComposable(
            navigateToTaskScreen = screen.task
        )
        taskComposable(
            navigateToListScreen = screen.list
        )
    }
}