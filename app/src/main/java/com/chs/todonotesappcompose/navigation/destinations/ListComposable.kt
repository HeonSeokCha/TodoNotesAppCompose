package com.chs.todonotesappcompose.navigation.destinations

import android.annotation.SuppressLint
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.chs.todonotesappcompose.utils.Constants.LIST_ARGUMENT_KEY
import com.chs.todonotesappcompose.utils.Constants.LIST_SCREEN
import com.chs.todonotesappcompose.utils.toAction
import com.chs.todonotesappcompose.view.ListScreen
import com.chs.todonotesappcompose.viewmodel.TodoNotesViewModel

@SuppressLint("UnrememberedMutableState")
@ExperimentalMaterialApi
fun NavGraphBuilder.listComposable(
    navigateToTaskScreen: (taskId: Int) -> Unit,
    todoNotesViewModel: TodoNotesViewModel
) {
    composable(
        route = LIST_SCREEN,
        arguments = mutableStateListOf(
            navArgument(LIST_ARGUMENT_KEY) {
                type = NavType.StringType
            }
        )
    ) { navBackStackEntry ->
        val action = navBackStackEntry.arguments?.getString(LIST_ARGUMENT_KEY).toAction()
        LaunchedEffect(key1 = action) {
            todoNotesViewModel.action.value = action
        }

        ListScreen(
            navigateToTaskScreen = navigateToTaskScreen,
            todoNotesViewModel = todoNotesViewModel
        )
    }
}