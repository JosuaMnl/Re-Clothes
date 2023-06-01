package com.c23ps422.reclothes.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector
import com.c23ps422.reclothes.R

sealed class Screen(val route: String, val labelRes: Int, val iconRes: ImageVector) {
    object Home : Screen("home", R.string.menu_home, Icons.Default.Home)
    object Detect : Screen("detect", R.string.menu_detect, Icons.Default.Search)
    object Transaction : Screen("transaction", R.string.menu_transaction, Icons.Default.Add)
    object Medals : Screen("medals", R.string.menu_medals, Icons.Default.Info)

    companion object {
        val items = listOf(Home, Detect, Transaction, Medals)
    }
}