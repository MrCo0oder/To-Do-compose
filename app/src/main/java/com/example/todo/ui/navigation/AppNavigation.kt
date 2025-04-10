package com.example.todo.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.toRoute
import com.example.todo.ui.navigation.destinations.listComposable
import com.example.todo.ui.navigation.destinations.taskComposable
import com.example.todo.ui.screens.SharedViewModel

@Composable
fun AppNavigation(
    navHostController: NavHostController,
    sharedViewModel: SharedViewModel,
) {

    NavHost(navController = navHostController, startDestination = Screen.ListScreen()) {
        listComposable(
            sharedViewModel = sharedViewModel,
            navigateToTaskScreen = {
                navHostController.navigate(Screen.TaskScreen(it))
            }
        )
        taskComposable(
            sharedViewModel = sharedViewModel,
            navigateToListScreen = {
                navHostController.navigate(Screen.ListScreen(it)) {
                    popUpTo(Screen.TaskScreen()) { inclusive = true }
                }
            }
        )
    }
}