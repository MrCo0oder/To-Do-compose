package com.example.todo.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todo.R
import com.example.todo.data.models.Priority
import com.example.todo.util.mediumPadding
import com.example.todo.util.priorityDropDownHeight

@Composable
fun PriorityDropDown(
    priority: Priority,
    onPrioritySelected: (Priority) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val angle: Float by animateFloatAsState(
        targetValue = if (expanded) -180f else 0f
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxWidth()
            .height(priorityDropDownHeight)
            .clickable { expanded = true }
            .border(
                1.dp,
                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f),
                MaterialTheme.shapes.large
            ),
    ) {
        PriorityItem(
            priority = priority,
            modifier = Modifier
                .weight(1f)
                .padding(start = mediumPadding)
        )
        IconButton(
            {
                expanded = true
            }, modifier = Modifier
                .alpha(0.6f)
                .rotate(angle)
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                tint = MaterialTheme.colorScheme.onBackground,
                contentDescription = stringResource(R.string.drop_down_arrow)
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                Modifier.fillMaxWidth(),
                containerColor = MaterialTheme.colorScheme.background
            ) {
                DropdownMenuItem(
                    onClick = {
                        expanded = false
                        onPrioritySelected(Priority.LOW)
                    },
                    text = { PriorityItem(priority = Priority.LOW) }
                )
                DropdownMenuItem(
                    onClick = {
                        expanded = false
                        onPrioritySelected(Priority.HIGH)
                    },
                    text = { PriorityItem(priority = Priority.HIGH) }
                )
                DropdownMenuItem(
                    onClick = {
                        expanded = false
                        onPrioritySelected(Priority.MEDIUM)
                    },
                    text = { PriorityItem(priority = Priority.MEDIUM) }
                )
            }
        }
    }
}

@Composable
@Preview
fun PriorityDropDownPreview() {
    PriorityDropDown(priority = Priority.LOW, onPrioritySelected = {})
}
