package com.example.todo.ui.screens.list

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import com.example.todo.data.models.ToDoTask
import com.example.todo.util.extraSmallIconSize
import com.example.todo.util.largePadding
import com.example.todo.util.mediumPadding


@Composable
fun TaskItem(
    task: ToDoTask,
    navigateToTaskScreen: (taskId: Int) -> Unit,
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(mediumPadding)
            .clickable { navigateToTaskScreen(task.id) }
            .clip(MaterialTheme.shapes.large),
        color = task.priority.color.copy(0.1f),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(largePadding),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = largePadding)
            ) {
                Row {
                    Text(
                        text = task.title,
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )
                    Canvas(modifier = Modifier.padding(largePadding)) {
                        drawCircle(
                            color = task.priority.color,
                            radius = extraSmallIconSize.toPx() / 2f
                        )
                    }
                }
                Text(
                    text = task.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2,
                    minLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }

}