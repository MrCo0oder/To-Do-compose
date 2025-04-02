package com.example.todo.ui.screens.list

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.todo.R

@Composable
fun TaskFloatingActionButton(navigateToTaskScreen: (taskId: Int) -> Unit) {
    FloatingActionButton(onClick = { navigateToTaskScreen(-1) }) {
        Icon(imageVector = Icons.Filled.Add, contentDescription = stringResource(R.string.add_task))
    }
}