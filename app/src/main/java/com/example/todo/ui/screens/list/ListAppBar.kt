package com.example.todo.ui.screens.list

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.wear.compose.material.ContentAlpha
import com.example.todo.R
import com.example.todo.data.models.Priority
import com.example.todo.ui.components.PriorityItem
import com.example.todo.ui.screens.SharedViewModel
import com.example.todo.util.TopBarState

@Composable
fun ListAppBar(
    sharedViewModel: SharedViewModel,
    topBarState: TopBarState,
    searchTextState: String
) {
    when (topBarState) {
        TopBarState.CLOSED -> {
            DefaultAppBar(
                onSearchClicked = {
                    sharedViewModel.updateSearchAppBarState(TopBarState.OPENED)
                },
                onSortClicked = {
                    when (it) {
                        Priority.LOW -> sharedViewModel.sortByLowPriority()
                        Priority.HIGH -> sharedViewModel.sortByHighPriority()
                        else -> sharedViewModel.getAllTasks()
                    }
                },
                onDeleteAllClicked = {
                    sharedViewModel.deleteAllTasks()
                }
            )
        }

        else -> {
            SearchAppBar(
                text = searchTextState,
                onTextChange = { sharedViewModel.updateSearchText(it) },
                onCloseClicked = {
                    sharedViewModel.updateSearchAppBarState(TopBarState.CLOSED)
                    sharedViewModel.updateSearchText("")
                },
                onSearchClicked = {
                    sharedViewModel.searchDatabase(it)
                })
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultAppBar(
    onSearchClicked: () -> Unit = {},
    onSortClicked: (Priority) -> Unit = {},
    onDeleteAllClicked: () -> Unit = {}
) {
    TopAppBar(
        title = {
            Text(text = stringResource(R.string.tasks))
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimary
        ),
        actions = {
            ListAppBarActions(
                onSearchClicked,
                onSortClicked,
                onDeleteAllClicked
            )
        }
    )
}

@Composable
fun ListAppBarActions(
    onSearchClicked: () -> Unit,
    onSortClicked: (Priority) -> Unit,
    onDeleteAllClicked: () -> Unit,
) {
    SearchAction(onSearchClicked)
    SortAction(onSortClicked)
    DeleteAllAction(onDeleteAllClicked)
}

@Composable
fun SearchAction(onActionClicked: () -> Unit) {
    IconButton(onClick = onActionClicked) {
        Icon(
            imageVector = Icons.Filled.Search,
            contentDescription = stringResource(R.string.search_button)
        )
    }
}

@Composable
fun SortAction(onActionClicked: (Priority) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    IconButton(onClick = { expanded = true }) {
        Icon(
            painter = painterResource(id = R.drawable.ic_filter),
            contentDescription = "Sort Button"
        )
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            DropdownMenuItem(
                text = { PriorityItem(priority = Priority.LOW) },
                onClick = {
                    expanded = false
                    onActionClicked(Priority.LOW)
                }
            )
            DropdownMenuItem(
                text = { PriorityItem(priority = Priority.HIGH) },
                onClick = {
                    expanded = false
                    onActionClicked(Priority.HIGH)
                }
            )
            DropdownMenuItem(
                text = { PriorityItem(priority = Priority.NONE) },
                onClick = {
                    expanded = false
                    onActionClicked(Priority.NONE)
                })
        }
    }
}

@Composable
fun DeleteAllAction(onActionClicked: () -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    IconButton(onClick = { expanded = true }) {
        Icon(
            imageVector = Icons.Filled.MoreVert,
            contentDescription = stringResource(R.string.delete_all_button)
        )
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            DropdownMenuItem(
                text = { Text(text = stringResource(R.string.delete_all)) },
                onClick = {
                    expanded = false
                    onActionClicked()
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchAppBar(
    text: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit
) {
    TopAppBar(
        title = {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = text,
                onValueChange = onTextChange,
                placeholder = {
                    Text(
                        text = stringResource(R.string.search_here),
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.alpha(ContentAlpha.medium),
                    )
                },
                textStyle = TextStyle(
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    fontStyle = MaterialTheme.typography.titleMedium.fontStyle
                ),
                singleLine = true,
                leadingIcon = {
                    Icon(
                        modifier = Modifier.alpha(ContentAlpha.medium),
                        imageVector = Icons.Filled.Search,
                        contentDescription = stringResource(R.string.search_button),
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        onSearchClicked(text)
                    }
                ),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    cursorColor = MaterialTheme.colorScheme.onPrimary,
                    focusedContainerColor = MaterialTheme.colorScheme.primary,
                    unfocusedContainerColor = MaterialTheme.colorScheme.primary,
                    disabledContainerColor = MaterialTheme.colorScheme.primary,
                    unfocusedIndicatorColor = Color.Transparent,
                    unfocusedLeadingIconColor = MaterialTheme.colorScheme.onPrimary,
                    unfocusedTrailingIconColor = MaterialTheme.colorScheme.onPrimary,
                )
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimary
        ),
        actions = {
            IconButton(
                onClick = {
                    if (text.isNotEmpty()) {
                        onTextChange("")
                    } else {
                        onCloseClicked()
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "close button",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    )
}

@Preview
@Composable
fun DefaultAppBarPreview() {
    DefaultAppBar()
}

@Composable
@Preview
private fun SearchAppBarPreview() {
    SearchAppBar(
        text = "Search",
        onTextChange = {},
        onCloseClicked = {},
        onSearchClicked = {}
    )
}