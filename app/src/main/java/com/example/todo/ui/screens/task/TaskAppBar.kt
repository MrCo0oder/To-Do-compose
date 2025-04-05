package com.example.todo.ui.screens.task

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.example.todo.R
import com.example.todo.data.models.Priority
import com.example.todo.data.models.ToDoTask
import com.example.todo.util.Action

@Composable
fun TaskAppBar(
    selectedTask: ToDoTask? = null,
    navigateToListScreen: (Action) -> Unit,
) {
    when (selectedTask) {
        null -> NewTaskAppBar(navigateToListScreen = navigateToListScreen)
        else -> EditTaskAppBar(
            selectedTask = selectedTask,
            navigateToListScreen = navigateToListScreen
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewTaskAppBar(
    navigateToListScreen: (Action) -> Unit,
) {
    TopAppBar(
        navigationIcon = {
            BackAction {
                navigateToListScreen(Action.NoAction)
            }
        },
        title = {
            Text(text = stringResource(R.string.add_new_task))
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimary
        ),
        actions = {
            AddTaskAction {
                navigateToListScreen(Action.Add)
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTaskAppBar(
    selectedTask: ToDoTask,
    navigateToListScreen: (Action) -> Unit,
) {
    TopAppBar(
        navigationIcon = {
            BackAction {
                navigateToListScreen(Action.NoAction)
            }
        },
        title = {
            Text(
                text = selectedTask.title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onPrimary
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimary
        ),
        actions = {
            DeleteTaskAction {
                navigateToListScreen(Action.Delete)
            }
            AddTaskAction {
                navigateToListScreen(Action.Update)
            }
        }
    )
}

@Composable
fun DeleteTaskAction(deleteAction: () -> Unit) {
    IconButton(onClick = deleteAction) {
        Icon(
            imageVector = Icons.Filled.Delete,
            tint = MaterialTheme.colorScheme.onPrimary,
            contentDescription = stringResource(R.string.delete_task),
        )
    }
}

@Composable
fun AddTaskAction(onActionClicked: () -> Unit) {
    IconButton(onClick = onActionClicked) {
        Icon(
            imageVector = Icons.Filled.Check,
            tint = MaterialTheme.colorScheme.onPrimary,
            contentDescription = stringResource(R.string.add_new_task)
        )
    }
}


@Composable
fun BackAction(onActionClicked: () -> Unit) {
    IconButton(onClick = onActionClicked) {
        Icon(
            imageVector = Icons.Filled.Clear,
            tint = MaterialTheme.colorScheme.onPrimary,
            contentDescription = stringResource(R.string.back_button),
        )
    }
}


@Preview
@Composable
fun DefaultAppBarPreview() {
    TaskAppBar(
        selectedTask = ToDoTask(
            id = 4690,
            title = "quas",
            description = "eget",
            priority = Priority.MEDIUM
        )
    ) {

    }
}