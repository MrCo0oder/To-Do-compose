package com.example.todo.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.todo.ui.navigation.destinations.listComposable
import com.example.todo.ui.navigation.destinations.taskComposable
import com.example.todo.ui.screens.SharedViewModel
import com.example.todo.ui.screens.settings.SettingsScreen

@Composable
fun AppNavigation(
    navHostController: NavHostController,
    sharedViewModel: SharedViewModel,
) {
    NavHost(navController = navHostController, startDestination = Screen.ListScreen) {
        listComposable(
            sharedViewModel = sharedViewModel,
            navigateToTaskScreen = {
                navHostController.navigate(Screen.TaskScreen(it))
            },
            navigateToSettingsScreen = {
                navHostController.navigate(Screen.SettingsScreen)
            }
        )
        taskComposable(
            sharedViewModel = sharedViewModel,
            navigateToListScreen = { action ->
                sharedViewModel.onAction(action)
                navHostController.popBackStack(
                    route = Screen.ListScreen,
                    inclusive = false
                )
            }
        )
        composable<Screen.SettingsScreen> {
            SettingsScreen(sharedViewModel)
        }
    }
}