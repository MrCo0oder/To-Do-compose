package com.example.todo.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.todo.ui.navigation.destinations.listComposable
import com.example.todo.ui.navigation.destinations.taskComposable
import com.example.todo.util.Action

@Composable
fun AppNavigation(
    navHostController: NavHostController,

    ) {

    NavHost(navController = navHostController, startDestination =Screen.ListScreen()) {
        listComposable(
            navigateToTaskScreen = {
                navHostController.navigate(Screen.TaskScreen(it))
            }
        )
        taskComposable(
            navigateToListScreen ={
                navHostController.navigate(Screen.ListScreen(it)){
                    popUpTo(Screen.TaskScreen()){inclusive = true}
                }
            }
        )
    }
}