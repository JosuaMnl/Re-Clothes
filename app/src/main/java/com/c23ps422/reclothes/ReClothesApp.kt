package com.c23ps422.reclothes

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.c23ps422.reclothes.ui.components.ReBottomNavigation
import com.c23ps422.reclothes.ui.components.ReButtonFullRounded
import com.c23ps422.reclothes.ui.navigation.Screen
import com.c23ps422.reclothes.ui.screen.DetectScreen
import com.c23ps422.reclothes.ui.screen.HomeScreen
import com.c23ps422.reclothes.ui.screen.diy.DetailDIYScreen
import com.c23ps422.reclothes.ui.screen.medals.MedalsScreen
import com.c23ps422.reclothes.ui.screen.saleprocess.ChooseImage
import com.c23ps422.reclothes.ui.screen.saleprocess.DataAllClothesScreen
import com.c23ps422.reclothes.ui.screen.saleprocess.PreviewTakenImage
import kotlinx.coroutines.launch
import java.io.File
import java.util.concurrent.ExecutorService

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ReClothesApp(
    outputDirectory: File,
    cameraExecutor: ExecutorService,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val capturedImageUri = remember { mutableStateOf<Uri?>(null) }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()

    val context = LocalContext.current
    val activity = context.findActivity()

    val currentDestination = navBackStackEntry?.destination

    if (currentDestination?.route == Screen.Home.route) {
        BackHandler {
            activity?.finish()
        }
    }

    val radioOptions = listOf(
        "Satuan (1-15 Pcs)",
        "Karungan/Plastik (>15 Pcs)",
    )

    var radioStatus by remember {
        mutableStateOf(0)
    }

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(text = "Banyak pakaian bekas yang ingin dijual?", fontSize = 24.sp)
                radioOptions.forEachIndexed { index, option: String ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clickable {
                                radioStatus = index
                            }
                            .fillMaxWidth()
                            .padding(4.dp)
                    ) {
                        RadioButton(
                            selected = radioStatus == index,
                            onClick = { radioStatus = index },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = Color.Green,
                                unselectedColor = Color.Gray,
                                disabledColor = Color.DarkGray
                            )
                        )
                        Spacer(modifier = Modifier.size(16.dp))
                        Text(
                            option
                        )
                    }
                }
                Spacer(modifier = Modifier.size(8.dp))
                ReButtonFullRounded(
                    text = "Continue",
                    onClick = {
                        scope.launch {
                            sheetState.hide()
                        }
                        if (radioStatus == 0) {
                            navController.navigate(Screen.ChooseImage.route)
                            Log.d(
                                "Choosen",
                                "Radiostatus with $radioStatus is clicked"
                            ) //take this into
                        } else if (radioStatus == 1) {
                            Log.d("Choosen", "Radiostatus with $radioStatus is clicked")
                            navController.navigate(Screen.DataAllClothes.route)
                        }
                    }
                )
            }
        },
        sheetShape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
    ) {
        Scaffold(
            bottomBar = {
                // Tsukifell: While the page on DetailDIY screen, hide the bottomBar
                if (currentDestination?.route !in listOf(
                        Screen.DetailDIY.route,
                        Screen.TakeImage.route,
                        Screen.PreviewTakenImage.route
                    )
                ) {
                    ReBottomNavigation(navController = navController)
                }
            },
            floatingActionButton = {
                // Tsukifell: While the page on DetailDIY screen, hide the fab too
                if (currentDestination?.route !in listOf(
                        Screen.DetailDIY.route,
                        Screen.TakeImage.route,
                        Screen.PreviewTakenImage.route
                    )
                ) {
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
                    HomeScreen(
                        navigateToDetail = { diyId ->
                            navController.navigate(Screen.DetailDIY.createRoute(diyId))
                        }
                    )
                }
                composable(Screen.Detect.route) {
                }
                composable(Screen.Transaction.route) {

                }
                composable(Screen.Medals.route) {
                    MedalsScreen()
                }
                composable(Screen.DataAllClothes.route) {
                    DataAllClothesScreen()
                }
                composable(Screen.PreviewTakenImage.route) {
                    PreviewTakenImage(photoUri = capturedImageUri.value)
                }
                composable(Screen.ChooseImage.route) {
                    ChooseImage {
                        navController.navigate(Screen.TakeImage.route)
                    }
                }
                composable(Screen.TakeImage.route) {
                    DetectScreen(
                        outputDirectory = outputDirectory,
                        cameraExecutor = cameraExecutor,
                        onImageCaptured = { uri ->
                            capturedImageUri.value = uri
                            Log.d("Navigation", "Navigating to PreviewTakenImage with URI: $uri")
                            Handler(Looper.getMainLooper()).post {
                                navController.navigate(Screen.PreviewTakenImage.route)
                            }
                        },
                        onError = { exception ->

                        },
                    )
                }
                composable(
                    route = Screen.DetailDIY.route,
                    arguments = listOf(navArgument("diyId") { type = NavType.IntType }),
                ) {
                    val id = it.arguments?.getInt("diyId") ?: -1
                    DetailDIYScreen(diyId = id, navigateBack = { navController.navigateUp() })
                }
            }
        }
    }
}

fun Context.findActivity(): Activity? {
    var currentContext = this
    while (currentContext is ContextWrapper) {
        if (currentContext is Activity) {
            return currentContext
        }
        currentContext = currentContext.baseContext
    }
    return null
}

//@Preview(showBackground = true)
//@Composable
//fun ReClothesAppPreview() {
//    ReClothesTheme {
//        ReClothesApp()
//    }
//}
