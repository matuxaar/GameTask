package com.example.gametask.ui.navigation

sealed class Screen(
    val route: String
) {
    object MenuScreen : Screen(ROUTE_MENU_SCREEN)
    object GameScreen : Screen(ROUTE_GAME_SCREEN)
    object EndGameScreen : Screen(ROUTE_END_GAME_SCREEN)

    private companion object {
        const val ROUTE_MENU_SCREEN = "menu_screen"
        const val ROUTE_GAME_SCREEN = "game_screen"
        const val ROUTE_END_GAME_SCREEN = "end_game_screen"
    }
}