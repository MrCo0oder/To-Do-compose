package com.example.todo.data.models

import androidx.compose.ui.graphics.Color
import com.example.todo.ui.theme.highPriorityColor
import com.example.todo.ui.theme.lowPriorityColor
import com.example.todo.ui.theme.mediumPriorityColor
import com.example.todo.ui.theme.nonePriorityColor

enum class Priority(val color: Color) {
    LOW(lowPriorityColor),
    MEDIUM(mediumPriorityColor),
    HIGH(highPriorityColor),
    NONE(nonePriorityColor)
}