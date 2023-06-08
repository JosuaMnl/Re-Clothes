package com.c23ps422.reclothes.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Detect : Screen("detect")
    object Transaction : Screen("transaction")
    object Medals : Screen("medals")

    object DataAllClothes: Screen("dataAllClothes")
}