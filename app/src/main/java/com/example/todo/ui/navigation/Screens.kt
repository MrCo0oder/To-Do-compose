package com.example.todo.ui.navigation

import androidx.navigation.NavHostController
import com.example.todo.util.Action

class Screens(navController: NavHostController) {
    companion object {
        const val LIST_SCREEN = "list/{action}"
        const val TASK_SCREEN = "task/{taskId}"
        const val SPLASH_SCREEN = "splash"
        const val LIST_ARGUMENT_KEY = "action"
        const val TASK_ARGUMENT_KEY = "taskId"
    }

    val list: (Action) -> Unit = { action ->
        navController.navigate("list/${action.javaClass.name}") {
            popUpTo(LIST_SCREEN) { inclusive = true }
        }
    }
    val task: (Int) -> Unit = { taskId ->
        navController.navigate("task/$taskId")
    }

}