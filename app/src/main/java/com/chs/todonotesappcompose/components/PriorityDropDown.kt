package com.chs.todonotesappcompose.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import com.chs.todonotesappcompose.database.model.Priority
import com.chs.todonotesappcompose.ui.theme.LARGEST_PADDING
import com.chs.todonotesappcompose.ui.theme.PRIORITY_DROP_DOWN_HEIGHT
import com.chs.todonotesappcompose.ui.theme.PRIORITY_INDICATOR_SIZE
import com.chs.todonotesappcompose.ui.theme.Typography
import kotlin.math.exp


@Composable
fun PriorityDropDown(
    priority: Priority,
    onPrioritySelected: (Priority) -> Unit
) {
    var expaneded by remember { mutableStateOf(false) }
    val angle: Float by animateFloatAsState(targetValue = if (expaneded) 180f else 0f)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.background)
            .height(PRIORITY_DROP_DOWN_HEIGHT)
            .clickable {
                expaneded = true
            }
            .border(
                width = 1.dp,
                color = MaterialTheme.colors.onSurface.copy(
                    alpha = ContentAlpha.disabled
                ),
                shape = MaterialTheme.shapes.small
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Canvas(
            modifier = Modifier
                .size(PRIORITY_INDICATOR_SIZE)
                .weight(1f)
        ) {
            drawCircle(color = priority.color)
        }
        Text(
            text = priority.name,
            modifier = Modifier
                .weight(8f),
            style = MaterialTheme.typography.subtitle2
        )
        IconButton(
            onClick = { expaneded = true },
            modifier = Modifier
                .alpha(ContentAlpha.medium)
                .rotate(degrees = angle)
                .weight(1.5f)
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = "Drop Down"
            )

            DropdownMenu(
                expanded = expaneded,
                onDismissRequest = {
                    expaneded = false
                },
                modifier = Modifier
                    .fillMaxWidth(fraction = 0.94f)
            ) {

                DropdownMenuItem(
                    onClick = {
                        expaneded = false
                        onPrioritySelected(Priority.HIGH)
                    }
                ) {
                    PriorityItem(priority = Priority.HIGH)
                }

                DropdownMenuItem(
                    onClick = {
                        expaneded = false
                        onPrioritySelected(Priority.MEDIUM)
                    }
                ) {
                    PriorityItem(priority = Priority.MEDIUM)
                }

                DropdownMenuItem(
                    onClick = {
                        expaneded = false
                        onPrioritySelected(Priority.Low)
                    }
                ) {
                    PriorityItem(priority = Priority.Low)
                }
            }
        }
    }
}

@Composable
fun PriorityItem(
    priority: Priority
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Canvas(
            modifier = Modifier.size(PRIORITY_INDICATOR_SIZE)
        ) {
            drawCircle(color = priority.color)
        }
        Text(
            text = priority.name,
            modifier = Modifier
                .padding(start = LARGEST_PADDING),
            style = Typography.subtitle2,
            color = MaterialTheme.colors.onSurface
        )
    }
}