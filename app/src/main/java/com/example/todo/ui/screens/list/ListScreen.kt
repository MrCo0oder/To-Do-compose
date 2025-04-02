package com.example.todo.ui.screens.list

import android.annotation.SuppressLint
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todo.R
import com.example.todo.ui.screens.SharedViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ListScreen(navigateToTaskScreen: (Int) -> Unit, sharedViewModel: SharedViewModel) {
    Scaffold(
        floatingActionButton = {
            TaskFloatingActionButton(navigateToTaskScreen)
        },
        topBar = {
            ListAppBar(
                sharedViewModel = sharedViewModel,
                topBarState = sharedViewModel.searchAppBarStateFlow.collectAsState().value,
                searchTextState = sharedViewModel.searchTextFlow.collectAsState().value
            )
        },
    ) {

    }
}


@Composable
fun TaskFloatingActionButton(navigateToTaskScreen: (taskId: Int) -> Unit) {
    FloatingActionButton(onClick = { navigateToTaskScreen(-1) }) {
        Icon(imageVector = Icons.Filled.Add, contentDescription = stringResource(R.string.add_task))
    }
}

@Composable
@Preview
fun ListScreenPreview() {
    ListScreen(navigateToTaskScreen = {}, sharedViewModel = viewModel())
}