package com.example.todo.ui.navigation.destinations

import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.todo.ui.navigation.Screen
import com.example.todo.ui.screens.SharedViewModel
import com.example.todo.ui.screens.list.ListScreen

fun NavGraphBuilder.listComposable(
    navigateToTaskScreen: (Int) -> Unit,
    sharedViewModel: SharedViewModel
) {
    composable<Screen.ListScreen> {
        val action = it.toRoute<Screen.ListScreen>().action
        LaunchedEffect(key1 = action) {
            sharedViewModel.onAction(action)
        }
        ListScreen(sharedViewModel = sharedViewModel, navigateToTaskScreen = navigateToTaskScreen)
    }
}