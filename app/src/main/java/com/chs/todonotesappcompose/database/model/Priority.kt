package com.chs.todonotesappcompose.database.model

import androidx.compose.ui.graphics.Color
import com.chs.todonotesappcompose.ui.theme.HighPriorityColor
import com.chs.todonotesappcompose.ui.theme.LowPriorityColor
import com.chs.todonotesappcompose.ui.theme.MediumPriorityColor
import com.chs.todonotesappcompose.ui.theme.NonePriorityColor

enum class Priority(val color: Color) {
    HIGH(HighPriorityColor),
    MEDIUM(MediumPriorityColor),
    Low(LowPriorityColor),
    NONE(NonePriorityColor)
}