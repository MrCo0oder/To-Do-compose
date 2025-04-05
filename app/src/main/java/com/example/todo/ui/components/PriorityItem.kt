package com.example.todo.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todo.data.models.Priority

@Composable
fun PriorityItem(priority: Priority, modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = CenterVertically,
        horizontalArrangement = spacedBy(12.dp),
        modifier = modifier
    ) {
        Canvas(Modifier.size(16.dp)) {
            drawCircle(color = priority.color)
        }
        Text(
            priority.name,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
@Preview
private fun PriorityItemPreview() {
    PriorityItem(priority = Priority.LOW)
}