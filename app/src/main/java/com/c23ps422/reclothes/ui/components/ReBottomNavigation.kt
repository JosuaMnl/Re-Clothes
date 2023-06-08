package com.c23ps422.reclothes.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
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
            Icon(imageVector = Icons.Default.Add, contentDescription = "fab")
        }
    }, floatingActionButtonPosition = FabPosition.Center, isFloatingActionButtonDocked = true) {

    }
}

@Composable
fun ReBottomNavigation(navController: NavHostController, modifier: Modifier = Modifier) {
    BottomAppBar(
        cutoutShape = CircleShape,
        modifier = modifier.height(72.dp),
        contentPadding = PaddingValues(4.dp)

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
                title = stringResource(R.string.menu_detect),
                icon = Icons.Default.Search,
                screen = Screen.Detect
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
        )
        navigationItems.map { item ->
            BottomNavigationItem(
                selectedContentColor = MaterialTheme.colors.onPrimary,
                unselectedContentColor = MaterialTheme.colors.onSecondary,
                alwaysShowLabel = false,
                selected = currentRoute == item.screen.route,
                onClick = {
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
//        BottomNavigation(
//            modifier = Modifier
//                .fillMaxHeight()
//                .fillMaxWidth(),
//            elevation = 8.dp,
//            backgroundColor = MaterialTheme.colors.secondary
//        ) {
//
//        }
    }
}
