package com.example.todo.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todo.data.models.Priority
import com.example.todo.ui.theme.Shapes
import com.example.todo.util.mediumPadding
import com.example.todo.util.smallPadding

@Composable
fun PriorityDropDown(
    priority: Priority,
    modifier: Modifier = Modifier,
    onPrioritySelected: (Priority) -> Unit
) {
    var mExpanded by remember { mutableStateOf(false) }
    Row(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background, Shapes.large)
            .wrapContentHeight()
            .border(
                1.dp,
                color = if (mExpanded) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f),
                shape = Shapes.large
            ),
    ) {
        Column(
            Modifier.padding(vertical = 13.dp, horizontal = mediumPadding),
            verticalArrangement = Arrangement.spacedBy(13.dp)
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .clip(Shapes.large)
                    .clickable { mExpanded = !mExpanded },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                PriorityItem(
                    priority = priority,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = mediumPadding)
                )
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.rotate(if (mExpanded) -180f else 0f)
                )
            }
            AnimatedVisibility(visible = mExpanded) {
                Column(
                    Modifier
                        .padding(horizontal = smallPadding),
                    verticalArrangement = Arrangement.spacedBy(13.dp)
                ) {
                    HDivider(color = MaterialTheme.colorScheme.primary)
                    PriorityItem(
                        priority = Priority.LOW,
                        modifier = Modifier
                            .wrapContentHeight()
                            .clip(Shapes.large)
                            .fillMaxWidth()
                            .apply {
                                if (priority == Priority.LOW) {
                                    background(
                                        MaterialTheme.colorScheme.primary.copy(0.2f),
                                        Shapes.large
                                    )
                                }
                            }
                            .clickable {
                                onPrioritySelected(Priority.LOW)
                                mExpanded = false
                            }
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                    HDivider(color = MaterialTheme.colorScheme.primary)
                    PriorityItem(
                        priority = Priority.MEDIUM,
                        modifier = Modifier
                            .wrapContentHeight()
                            .fillMaxWidth()
                            .apply {
                                if (priority == Priority.MEDIUM) {
                                    background(
                                        MaterialTheme.colorScheme.primary.copy(0.2f),
                                        Shapes.large
                                    )
                                }
                            }
                            .clickable {
                                onPrioritySelected(Priority.MEDIUM)
                                mExpanded = false
                            }
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                    HDivider(color = MaterialTheme.colorScheme.primary)
                    PriorityItem(
                        priority = Priority.HIGH,
                        modifier = Modifier
                            .wrapContentHeight()
                            .clip(Shapes.large)
                            .fillMaxWidth()
                            .apply {
                                if (priority == Priority.HIGH) {
                                    background(
                                        MaterialTheme.colorScheme.primary.copy(0.2f),
                                        Shapes.large
                                    )
                                }
                            }
                            .clickable {
                                onPrioritySelected(Priority.HIGH)
                                mExpanded = false
                            }
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }
            }

        }
    }
}

@Composable
fun HDivider(modifier: Modifier = Modifier, color: Color = Color.LightGray) {
    Box(
        modifier = modifier
            .background(color)
            .height(1.dp)
            .fillMaxWidth()
    )
}

@Composable
@Preview
fun CustomDropDownPreview() {
    PriorityDropDown(Priority.HIGH, onPrioritySelected = {})
}