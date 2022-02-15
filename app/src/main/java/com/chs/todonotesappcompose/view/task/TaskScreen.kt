package com.chs.todonotesappcompose.view.task

import android.widget.Toast
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import com.chs.todonotesappcompose.database.model.Priority
import com.chs.todonotesappcompose.database.model.ToDoTask
import com.chs.todonotesappcompose.utils.Action
import com.chs.todonotesappcompose.viewmodel.TodoNotesViewModel

@Composable
fun TaskScreen(
    selectTask: ToDoTask?,
    toDooNotesViewModel: TodoNotesViewModel,
    navigateToListScreen: (Action) -> Unit
) {
    val title: String by toDooNotesViewModel.title
    val description: String by toDooNotesViewModel.description
    val priority: Priority by toDooNotesViewModel.priority
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TaskAppBar(
                selectedTask = selectTask,
                navigateToListScreen = { action ->
                    if (action == Action.NO_ACTION) {
                        navigateToListScreen(action)
                    } else {
                        if (toDooNotesViewModel.validateFields()) {
                            navigateToListScreen(action)
                        } else {
                            Toast.makeText(context, "Fields Empty", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            )
        },
        content = {
            TaskContent(
                title = title,
                onTitleChange = {
                    toDooNotesViewModel.updateTitle(it)
                },
                description = description,
                onDescriptionChange = {
                    toDooNotesViewModel.description.value = it
                },
                priority = priority,
                onPrioritySelected = {
                    toDooNotesViewModel.priority.value = it
                }

            )
        }
    )
}