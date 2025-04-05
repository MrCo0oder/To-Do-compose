package com.example.todo.ui.screens.task

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todo.data.models.ToDoTask
import com.example.todo.ui.components.ErrorContent
import com.example.todo.ui.components.LoadingContent
import com.example.todo.ui.components.PriorityDropDown
import com.example.todo.ui.screens.SharedViewModel
import com.example.todo.util.Action
import com.example.todo.util.RequestState

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
                    toDoTaskState.data?.let { it1 ->
                        TaskContent(
                            paddingValues = it,
                            selectedTask = it1,
                            sharedViewModel = sharedViewModel
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
    selectedTask: ToDoTask,
    sharedViewModel: SharedViewModel,
    paddingValues: PaddingValues
) {
    Column(modifier = Modifier.padding(top = paddingValues.calculateTopPadding())) {
        val priority = remember { mutableStateOf(selectedTask.priority) }
        PriorityDropDown(priority = priority.value) {
            priority.value = it
        }

    }
}


@Composable
@Preview
fun ListScreenPreview() {
    TaskScreen(navigateToListScreen = {}, sharedViewModel = viewModel(), RequestState.Loading)
}