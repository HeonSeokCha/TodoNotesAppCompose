package com.chs.todonotesappcompose.view

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.chs.todonotesappcompose.R
import com.chs.todonotesappcompose.ui.theme.fabBackgroundColor
import com.chs.todonotesappcompose.utils.Action
import com.chs.todonotesappcompose.utils.SearchAppBarState
import com.chs.todonotesappcompose.viewmodel.TodoNotesViewModel
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun ListScreen (
    navigateToTaskScreen: (taskId: Int) -> Unit,
    todoNotesViewModel: TodoNotesViewModel
) {
    LaunchedEffect(key1 = true) {
        todoNotesViewModel.getAllTasks()
        todoNotesViewModel.readSortState()
    }

    val action by todoNotesViewModel.action
    val allTasks by todoNotesViewModel.allTask.collectAsState()
    val sortState by todoNotesViewModel.sortState.collectAsState()
    val lowPriority by todoNotesViewModel.lowPriorityTask.collectAsState()
    val highPriority by todoNotesViewModel.highPriorityTask.collectAsState()
    val searchedTask by todoNotesViewModel.searchedTasks.collectAsState()
    val searchAppBarState: SearchAppBarState by todoNotesViewModel.searchAppBarState
    val searchTextState: String by todoNotesViewModel.searchTextState
    val scaffoldState = rememberScaffoldState()

    DisplaySnackBar(
        scaffoldState = scaffoldState,
        handleDatabaseAction = {
            todoNotesViewModel.handleDatabaseActions(action = action)
        },
        onUndoClicked = {
            todoNotesViewModel.action.value = it
        },
        taskTitle = todoNotesViewModel.title.value,
        action = action
    )

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            ListAppBar(
                todoNotesViewModel = todoNotesViewModel,
                searchAppBarState = searchAppBarState,
                searchTextState = searchTextState
            )
        },
        content = {
            ListContent(
                allTasks = allTasks,
                searchedTask = searchedTask,
                lowPriority = lowPriority,
                highPriority = highPriority,
                searchAppBarState = searchAppBarState,
                sortState = sortState,
                onSwipeToDelete = { action, task ->
                    todoNotesViewModel.action.value = action
                    todoNotesViewModel.updateTaskField(selectedTask = task)
                },
                navigateToTaskScreen = navigateToTaskScreen
            )
        },
        floatingActionButton = {
            ListFab(onFabClicked = navigateToTaskScreen)
        }
    )
}

@Composable
fun ListFab(
    onFabClicked: (taskId: Int) -> Unit
) {
    FloatingActionButton(onClick = {
        onFabClicked(-1)
    },
        backgroundColor = MaterialTheme.colors.fabBackgroundColor
    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = stringResource(id = R.string.add_button),
            tint = Color.White
        )
    }
}

@Composable
fun DisplaySnackBar(
    scaffoldState: ScaffoldState,
    handleDatabaseAction: () -> Unit,
    onUndoClicked: (Action) -> Unit,
    taskTitle: String,
    action: Action
) {
    handleDatabaseAction()
    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = action) {
        if (action != Action.NO_ACTION) {
            scope.launch {
                val snackBarResult = scaffoldState.snackbarHostState.showSnackbar(
                    message = setMessage(action = action, taskTitle = taskTitle),
                    actionLabel = setActionLabel(action)
                )
                undoDeDeletedTask(action,
                    snackBarResult = snackBarResult,
                    onUndoClicked = onUndoClicked)
            }
        }
    }
}

private fun setMessage(action: Action, taskTitle: String) :String {
    return when(action) {
        Action.DELETE_ALL -> "All task Removed"
        else -> "${action.name}: $taskTitle"
    }
}

private fun setActionLabel(action: Action): String {
    return if (action.name == "DELETE") "UNDO" else "OK"
}

private fun undoDeDeletedTask(
    action: Action,
    snackBarResult: SnackbarResult,
    onUndoClicked: (Action) -> Unit,
) {
    if (snackBarResult ==  SnackbarResult.ActionPerformed && action ==Action.DELETE) {
        onUndoClicked(Action.UNDO)
    }
}