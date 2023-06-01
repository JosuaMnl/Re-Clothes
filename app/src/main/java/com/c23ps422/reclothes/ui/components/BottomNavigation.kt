package com.c23ps422.reclothes.ui.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.c23ps422.reclothes.ui.navigation.Screen

@Preview
@Composable
fun BottomNavigationPreview() {
    val navController = rememberNavController()

    Scaffold(bottomBar = {
        BottomBar(navController = navController)
    }, floatingActionButton = {
        FloatingActionButton(onClick = { }) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "fab")
        }
    }, floatingActionButtonPosition = FabPosition.Center, isFloatingActionButtonDocked = true) {

    }
}

@Composable
fun BottomBar(navController: NavController, modifier: Modifier = Modifier) {
    BottomAppBar(cutoutShape = CircleShape) {
        val currentRoute = navController.currentDestination?.route

        Screen.items.forEach { screen ->
            BottomNavigationItem(
                selected = (currentRoute == screen.route),
                modifier = modifier.weight(1f),
                icon = {
                    Icon(
                        imageVector = screen.iconRes,
                        contentDescription = screen.route
                    )
                },
                label = { Text(stringResource(screen.labelRes)) },
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.startDestinationId)
                    }
                }
            )
        }
    }
}
