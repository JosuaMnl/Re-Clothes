package com.c23ps422.reclothes.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Detect : Screen("detect")
    object Transaction : Screen("transaction")
    object Medals : Screen("medals")
    object DetailDIY : Screen("home/{diyId}") {
        fun createRoute(diyId: Int) = "home/$diyId"
    }

    object DataAllClothes : Screen("dataAllClothes")
    object ChooseImage : Screen("chooseImage")
    object TakeImage : Screen("takeImage")
    object PreviewTakenImage : Screen("previewTakenImage")
}