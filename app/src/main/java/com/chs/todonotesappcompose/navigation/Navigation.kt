package com.chs.todonotesappcompose.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.chs.todonotesappcompose.utils.Constants.LIST_SCREEN
import com.chs.todonotesappcompose.viewmodel.TodoNotesViewModel

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

    }
}