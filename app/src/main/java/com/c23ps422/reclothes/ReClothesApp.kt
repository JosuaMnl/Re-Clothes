package com.c23ps422.reclothes

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.c23ps422.reclothes.ui.components.ReBottomNavigation
import com.c23ps422.reclothes.ui.navigation.NavigationItem
import com.c23ps422.reclothes.ui.navigation.Screen
import com.c23ps422.reclothes.ui.screen.HomeContent
import com.c23ps422.reclothes.ui.screen.MedalsScreen
import com.c23ps422.reclothes.ui.theme.ReClothesTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ReClothesApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .height(300.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(text = "Bottom sheet", fontSize = 60.sp)
            }
        },
        sheetShape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
    ) {
        Scaffold(
            bottomBar = {
                ReBottomNavigation(navController = navController)
            },
            floatingActionButton = {
                FloatingActionButton(onClick = {
                    scope.launch {
                        if (sheetState.isVisible) {
                            sheetState.hide()
                        } else {
                            sheetState.show()
                        }
                    }
                }) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "fab")
                }
            },
            floatingActionButtonPosition = FabPosition.Center,
            isFloatingActionButtonDocked = true,
            modifier = modifier
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = Screen.Home.route,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(Screen.Home.route) {
                    HomeContent()
                }
                composable(Screen.Detect.route) {

                }
                composable(Screen.Transaction.route) {

                }
                composable(Screen.Medals.route) {
                    MedalsScreen()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ReClothesAppPreview() {
    ReClothesTheme {
        ReClothesApp()
    }
}