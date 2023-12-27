package com.example.gametask.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun AppNavGraph(
    navHostController: NavHostController,
    menuScreenContent: @Composable () -> Unit,
    gameScreenContent: @Composable () -> Unit,
    endGameContent: @Composable () -> Unit
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.MenuScreen.route
    ) {
        composable(Screen.MenuScreen.route) {
            menuScreenContent()
        }
        composable(Screen.GameScreen.route) {
            gameScreenContent()
        }
        composable(Screen.EndGameScreen.route) {
            endGameContent()
        }
    }
}