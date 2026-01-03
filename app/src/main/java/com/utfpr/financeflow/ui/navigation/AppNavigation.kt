package com.utfpr.financeflow.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.utfpr.financeflow.viewmodel.TransactionViewModel
import com.utfpr.financeflow.ui.screens.AddTransactionScreen
import com.utfpr.financeflow.ui.screens.ListTransactionScreen

object Routes {
    const val ADICIONAR = "adicionar"
    const val EXTRATO = "extrato"
}

@Composable
fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val sharedViewModel: TransactionViewModel = viewModel()
    NavHost(
        navController = navController,
        startDestination = Routes.ADICIONAR,
        modifier = modifier
    ) {
        composable(Routes.ADICIONAR) {
            AddTransactionScreen(viewModel = sharedViewModel)
        }
        composable(Routes.EXTRATO) {
            ListTransactionScreen(viewModel = sharedViewModel)
        }
    }
}