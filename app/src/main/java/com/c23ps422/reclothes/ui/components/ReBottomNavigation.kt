package com.c23ps422.reclothes.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.c23ps422.reclothes.R
import com.c23ps422.reclothes.ui.navigation.NavigationItem
import com.c23ps422.reclothes.ui.navigation.Screen

@Preview
@Composable
fun ReBottomNavigationPreview() {
    val navController = rememberNavController()

    Scaffold(bottomBar = {
        ReBottomNavigation(navController = navController)
    }, floatingActionButton = {
        FloatingActionButton(onClick = { }) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = stringResource(R.string.rbn_fab_desc)
            )
        }
    }, floatingActionButtonPosition = FabPosition.Center, isFloatingActionButtonDocked = true) {
        it
    }
}

@Composable
fun ReBottomNavigation(navController: NavHostController, modifier: Modifier = Modifier) {
    BottomAppBar(
        cutoutShape = CircleShape,
        modifier = modifier.height(72.dp),
        contentPadding = PaddingValues(4.dp),
        backgroundColor = Color(android.graphics.Color.parseColor("#27360B"))

    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        val navigationItems = listOf(
            NavigationItem(
                title = stringResource(R.string.menu_home),
                icon = Icons.Default.Home,
                screen = Screen.Home
            ),
            NavigationItem(
                title = stringResource(R.string.menu_transaction),
                icon = Icons.Default.Menu,
                screen = Screen.Transaction
            ),
            NavigationItem(
                title = stringResource(R.string.menu_medals),
                icon = Icons.Default.Star,
                screen = Screen.Medals
            ),
            NavigationItem(
                title = stringResource(R.string.menu_profile),
                icon = Icons.Default.Person,
                screen = Screen.UserScreen
            ),
        )
        navigationItems.map { item ->
            BottomNavigationItem(
                selectedContentColor = Color.White,
                unselectedContentColor = Color.Gray,
                alwaysShowLabel = false,
                selected = currentRoute == item.screen.route,
                onClick = {
                    if (currentRoute != Screen.Home.route) {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(navController.graph.startDestinationId)
                        }
                    }
                    navController.navigate(item.screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                },
                icon = {
                    Icon(imageVector = item.icon, contentDescription = item.title)
                },
                label = {
                    Text(text = item.title)
                },
            )
        }
    }
}
