package com.chs.todonotesappcompose.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.chs.todonotesappcompose.navigation.destinations.listComposable
import com.chs.todonotesappcompose.navigation.destinations.taskComposable
import com.chs.todonotesappcompose.utils.Constants.LIST_SCREEN
import com.chs.todonotesappcompose.viewmodel.TodoNotesViewModel

@ExperimentalMaterialApi
@Composable
fun ToDoNotesNavigation(
    navHostController: NavHostController,
    todoNotesViewModel: TodoNotesViewModel
) {
    val screens = remember(navHostController) { Screens(navHostController) }

    NavHost(
        navController = navHostController,
        startDestination = LIST_SCREEN
    ) {
        listComposable(
            navigateToTaskScreen = screens.task,
            todoNotesViewModel = todoNotesViewModel
        )
        taskComposable(
            todoNotesViewModel = todoNotesViewModel,
            navigateToListScreen = screens.list
        )
    }
}