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
import androidx.compose.runtime.LaunchedEffect
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
import com.c23ps422.reclothes.data.ReClothesPreference
import com.c23ps422.reclothes.ui.components.ReBottomNavigation
import com.c23ps422.reclothes.ui.components.ReButtonFullRounded
import com.c23ps422.reclothes.ui.navigation.Screen
import com.c23ps422.reclothes.ui.screen.DetectScreen
import com.c23ps422.reclothes.ui.screen.HomeScreen
import com.c23ps422.reclothes.ui.screen.Register
import com.c23ps422.reclothes.ui.screen.SplashScreen
import com.c23ps422.reclothes.ui.screen.Welcome
import com.c23ps422.reclothes.ui.screen.diy.DetailDIYScreen
import com.c23ps422.reclothes.ui.screen.login.Login
import com.c23ps422.reclothes.ui.screen.login.dataStore
import com.c23ps422.reclothes.ui.screen.medals.MedalsScreen
import com.c23ps422.reclothes.ui.screen.saleprocess.ChooseImage
import com.c23ps422.reclothes.ui.screen.saleprocess.DataAllClothesScreen
import com.c23ps422.reclothes.ui.screen.saleprocess.PreviewTakenImage
import com.c23ps422.reclothes.ui.theme.ReClothesTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.io.File
import java.util.concurrent.ExecutorService

/**
 * This is the root composable function for the ReClothes app.
 *
 * @param navController NavController object to handle navigation within the app.
 *                      Default value is a newly created NavController that is remembered across recompositions.
 *
 * Inside this function:
 * - The current Android context is obtained using LocalContext.current.
 * - An instance of ReClothesPreference is obtained, which appears to handle shared preferences for the app.
 * - A mutable state called "splashScreenFinished" is defined and initialized as false. This state is used to track
 *   when the SplashScreen has completed its display.
 * - SplashScreen is displayed until it's finished. Once the SplashScreen is done, "splashScreenFinished" is set to true.
 * - When "splashScreenFinished" is true, the NavGraph composable is displayed. This is presumably where the app's main content and navigation setup are located.
 */
@Composable
fun ReClothesApp(
    outputDirectory: File,
    cameraExecutor: ExecutorService,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {

    val context = LocalContext.current
    val pref = ReClothesPreference.getInstance(context.dataStore)

    var splashScreenFinished by remember {
        mutableStateOf(false)
    }

    SplashScreen(splashScreenFinished) {
        splashScreenFinished = true
    }

    if (splashScreenFinished) {
        NavGraph(
            navController = navController,
            pref = pref,
            outputDirectory = outputDirectory,
            cameraExecutor = cameraExecutor
        )
    }
}

/**
 * This is the composable function for managing the navigation graph of the ReClothes app.
 *
 * @param modifier The Modifier to be applied to the composable, which is used to modify its appearance or behavior.
 * @param navController NavController object to handle navigation within the app.
 * @param pref An instance of ReClothesPreference for managing app's using DataStore.
 *
 * The function is annotated with @OptIn(ExperimentalMaterialApi::class) because it uses some experimental features of Jetpack Compose Material library.
 *
 * Inside this function:
 * - Current back stack entry and route are obtained from the NavController.
 * - A ModalBottomSheetState is remembered for handling the modal bottom sheet behavior.
 * - CoroutineScope is remembered for launching coroutines.
 * - The current Android context and the corresponding Activity are obtained.
 * - BackHandler is used to handle system back button events. If the current route is the Home route, pressing back button will finish the activity.
 * - A list of routes where the bottom bar and floating action button should not be displayed is defined.
 * - A mutable state for controlling the visibility of bottom bar and floating action button is remembered and initialized.
 * - An effect is launched to delay the appearance of bottom bar and floating action button by 500 milliseconds.
 * - A ModalBottomSheetLayout is used as the root layout. Inside it, a Scaffold is used for main content layout and navigation setup.
 * - The navigation setup includes different screens of the app represented by different routes.
 * - A launched effect is used to manage the initial route based on whether a token exists in shared preferences or not.
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    pref: ReClothesPreference,
    outputDirectory: File,
    cameraExecutor: ExecutorService,
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

    val noBottomBarFab = listOf(
        Screen.Welcome.route,
        Screen.Login.route,
        Screen.Register.route,
        Screen.DetailDIY.route,
        Screen.Splash.route,
        Screen.ChooseImage.route,
        Screen.DataAllClothes.route,
        Screen.TakeImage.route,
        Screen.PreviewTakenImage.route
    )

    var showBottomBarFab by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = Unit) {
        delay(500)
        showBottomBarFab = true
    }

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {
            SheetContent(onContinueClick = { radioStatus ->
                scope.launch {
                    sheetState.hide()
                }
                if (radioStatus == 0) {
                    navController.navigate(Screen.ChooseImage.route)
                } else if (radioStatus == 1) {
                    navController.navigate(Screen.DataAllClothes.route)
                }
            })
        },
        sheetShape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
    ) {
        Scaffold(
            bottomBar = {
                // Tsukifell: While the page on DetailDIY screen, hide the bottomBar

                if (currentRoute !in noBottomBarFab && showBottomBarFab) {
                    ReBottomNavigation(navController = navController)
                }
            },
            floatingActionButton = {
                // Tsukifell: While the page on DetailDIY screen, hide the fab too
                if (currentRoute !in noBottomBarFab && showBottomBarFab) {
                    FloatingActionButton(onClick = {
                        scope.launch {
                            if (sheetState.isVisible) {
                                sheetState.hide()
                            } else {
                                sheetState.show()
                            }
                        }
                    }
                    ) {
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

                composable(Screen.Welcome.route) {
                    Welcome(
                        navigateToRegister = {
                            navController.navigate(Screen.Register.route)
                        },
                        navigateToLogin = {
                            navController.navigate(Screen.Login.route)
                        }
                    )
                }

                composable(Screen.Login.route) {
                    Login(navController)
                }

                composable(Screen.Register.route) {
                    Register()
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        when (pref.getToken().first()) {
            null -> navController.navigate(Screen.Welcome.route) {
                popUpTo(Screen.Home.route) { inclusive = true }
            }

            else -> {}
        }
    }
}

/**
 * Extension function to the Context class for retrieving the Activity that the context is currently tied to.
 *
 * This function iteratively checks the base context if it's an instance of an Activity.
 * If it is, it returns the current context cast as an Activity. If the base context is not an Activity,
 * it sets the base context of the current context as the new current context, and continues the loop.
 * If no Activity is found (i.e., the base context is not a ContextWrapper), it returns null.
 *
 * @return The Activity that the context is currently tied to, or null if no such Activity can be found.
 */
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

/**
 * A Composable function that represents a content sheet with a selectable radio button list.
 * This function displays a question and a set of radio buttons for each option. When one of the radio buttons is selected,
 * the corresponding index is set in the mutable state 'radioStatus'.
 * A "Continue" button is also displayed; when this button is clicked,
 * it invokes the provided 'onContinueClick' function with the current 'radioStatus' value.
 *
 * @param onContinueClick A higher-order function to be called when the "Continue" button is clicked.
 *                        This function should take an Int parameter representing the index of the selected radio button.
 * @param modifier The modifier to be applied to the Column composable.
 */
@Composable
fun SheetContent(
    onContinueClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val radioOptions = listOf(
        "Satuan (1-15 Pcs)",
        "Karungan/Plastik (>15 Pcs)",
    )

    var radioStatus by remember {
        mutableStateOf(0)
    }

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
            onClick = { onContinueClick(radioStatus) }
        )
    }
}