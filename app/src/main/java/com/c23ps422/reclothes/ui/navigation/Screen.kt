package com.c23ps422.reclothes.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Transaction : Screen("transaction")
    object Medals : Screen("medals")
    object DetailDIY : Screen("home/{diyId}") {
        fun createRoute(diyId: Int) = "home/$diyId"
    }
    object ChooseImage : Screen("chooseImage")
    object TakeImage : Screen("takeImage")
    object PreviewTakenImage : Screen("previewTakenImage")
    object DataAllClothes: Screen("dataAllClothes")
    object Welcome: Screen("welcome")
    object Login: Screen("login")
    object Register: Screen("register")
    object Splash: Screen("splash")
    object UserScreen: Screen("userScreen")
}