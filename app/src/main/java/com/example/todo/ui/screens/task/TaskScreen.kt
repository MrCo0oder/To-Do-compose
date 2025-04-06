package com.example.todo.ui.screens.task

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todo.R
import com.example.todo.data.models.Priority
import com.example.todo.data.models.ToDoTask
import com.example.todo.ui.components.ErrorContent
import com.example.todo.ui.components.LoadingContent
import com.example.todo.ui.components.PriorityDropDown
import com.example.todo.ui.screens.SharedViewModel
import com.example.todo.ui.screens.TaskScreenEvent
import com.example.todo.ui.theme.Shapes
import com.example.todo.util.Action
import com.example.todo.util.RequestState
import com.example.todo.util.smallPadding

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TaskScreen(
    navigateToListScreen: (Action) -> Unit,
    sharedViewModel: SharedViewModel,
    toDoTaskState: RequestState<ToDoTask?>,
) {
    when (toDoTaskState) {

        is RequestState.Success -> {
            Scaffold(
                topBar = {
                    TaskAppBar(
                        selectedTask = toDoTaskState.data,
                        navigateToListScreen = navigateToListScreen
                    )
                }, content =
                {
                    val task = sharedViewModel.taskFlow.collectAsState().value

                    if (toDoTaskState.data != null) {
                        val toDoTask = toDoTaskState.data
                        LaunchedEffect(key1 = Unit) {
                            sharedViewModel.setSelectedTask(toDoTask)
                        }
                        TaskContent(
                            paddingValues = it,
                            title = task?.title ?: toDoTask.title,
                            onTitleChange = { title ->
                                sharedViewModel.onEvent(
                                    TaskScreenEvent.SetTitle(
                                        title
                                    )
                                )
                            },
                            description = task?.description ?: toDoTask.description,
                            onDescriptionChange = { description ->
                                sharedViewModel.onEvent(
                                    TaskScreenEvent.SetDescription(
                                        description
                                    )
                                )
                            },
                            priority = task?.priority ?: toDoTask.priority,
                            onPrioritySelected = { priority ->
                                sharedViewModel.onEvent(
                                    TaskScreenEvent.SetPriority(
                                        priority
                                    )
                                )
                            },
                        )
                    } else {
                        TaskContent(
                            paddingValues = it,
                            title = task?.title ?: "",
                            onTitleChange = { title ->
                                sharedViewModel.onEvent(
                                    TaskScreenEvent.SetTitle(
                                        title
                                    )
                                )
                            },
                            description = task?.description ?: "",
                            onDescriptionChange = { description ->
                                sharedViewModel.onEvent(
                                    TaskScreenEvent.SetDescription(description)
                                )
                            },
                            priority = task?.priority ?: Priority.LOW,
                            onPrioritySelected = { priority ->
                                sharedViewModel.onEvent(
                                    TaskScreenEvent.SetPriority(priority)
                                )
                            },
                        )
                    }
                })
        }

        is RequestState.Error -> {
            ErrorContent(message = toDoTaskState.exception.message.toString()) {
                navigateToListScreen(Action.NoAction)
            }
        }

        else -> {
            LoadingContent()
        }
    }


}

@Composable
fun TaskContent(
    paddingValues: PaddingValues,
    title: String,
    onTitleChange: (String) -> Unit,
    description: String,
    onDescriptionChange: (String) -> Unit,
    priority: Priority,
    onPrioritySelected: (Priority) -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .background(MaterialTheme.colorScheme.background)
    ) {
        OutlinedTextField(
            value = title,
            onValueChange = {
                onTitleChange(it)
            },
            label = { Text(text = stringResource(R.string.title)) },
            singleLine = true,
            modifier = Modifier
                .padding(smallPadding)
                .fillMaxWidth()
                .padding(smallPadding),
            maxLines = 1,
            shape = Shapes.large,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
            )
        )
        PriorityDropDown(
            priority = priority,
            Modifier
                .padding(smallPadding)
                .fillMaxWidth()
                .padding(smallPadding),
            onPrioritySelected
        )
        OutlinedTextField(
            value = description,
            onValueChange = { onDescriptionChange(it) },
            label = { Text(text = stringResource(R.string.description)) },
            singleLine = false,
            modifier = Modifier
                .weight(1f)
                .padding(smallPadding)
                .fillMaxWidth()
                .padding(smallPadding),
//            placeholder = { Text(text = "Enter Description") },
            shape = Shapes.large,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
            )
        )

    }
}


@Composable
@Preview
fun ListScreenPreview() {
    TaskScreen(
        navigateToListScreen = {
        },
        sharedViewModel = viewModel(),
        toDoTaskState = RequestState.Success(
            ToDoTask(
                id = 1,
                title = "Title",
                description = "Description",
                priority = Priority.LOW
            )
        ),
    )
}