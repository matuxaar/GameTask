package com.example.gametask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import com.example.gametask.ui.endgamescreen.EndGameScreen
import com.example.gametask.ui.gamescreen.GameScreen
import com.example.gametask.ui.menuscreen.MenuScreen
import com.example.gametask.ui.navigation.AppNavGraph

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavigationApp()
        }
    }
}

@Composable
private fun NavigationApp() {
    val navHostController = rememberNavController()
    val coinViewModel = remember { CoinViewModel() }

    AppNavGraph(
        navHostController = navHostController,
        menuScreenContent = { MenuScreen(navHostController, coinViewModel) },
        gameScreenContent = { GameScreen(navHostController) },
        endGameContent = { EndGameScreen(navHostController, coinViewModel) }
    )
}

