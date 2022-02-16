package com.chs.todonotesappcompose.navigation.destinations

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.chs.todonotesappcompose.utils.Action
import com.chs.todonotesappcompose.utils.Constants.TASK_ARGUMENT_KEY
import com.chs.todonotesappcompose.utils.Constants.TASK_SCREEN
import com.chs.todonotesappcompose.view.task.TaskScreen
import com.chs.todonotesappcompose.viewmodel.TodoNotesViewModel

fun NavGraphBuilder.taskComposable(
    todoNotesViewModel: TodoNotesViewModel,
    navigateToListScreen: (Action) -> Unit
) {
    composable(
        route = TASK_SCREEN,
        arguments = mutableStateListOf(
            navArgument(TASK_ARGUMENT_KEY) {
                type = NavType.IntType
            }
        )
    ) { navBackStackEntry ->
        val taskId = navBackStackEntry.arguments!!.getInt(TASK_ARGUMENT_KEY)
        LaunchedEffect(key1 = taskId) {
            todoNotesViewModel.getSelectedTask(taskId)
        }
        val selectedTask by todoNotesViewModel.selectedTask.collectAsState()

        LaunchedEffect(key1 = selectedTask) {
            if (selectedTask != null || taskId == -1) {
                todoNotesViewModel.updateTaskField(selectedTask)
            }
        }

        TaskScreen(
            selectTask = selectedTask,
            toDooNotesViewModel = todoNotesViewModel,
            navigateToListScreen = navigateToListScreen
        )
    }
}