package com.example.todo.ui.navigation

import com.example.todo.util.Action
import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    data object ListScreen : Screen()

    @Serializable
    data class TaskScreen(val id: Int = -1) : Screen()
}