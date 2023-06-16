package com.c23ps422.reclothes.ui.screen.main

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.c23ps422.reclothes.R
import com.c23ps422.reclothes.helper.UiState
import com.c23ps422.reclothes.pref.ReClothesPreference
import com.c23ps422.reclothes.ui.components.ReBottomNavigation
import com.c23ps422.reclothes.ui.components.ReButtonFullRounded
import com.c23ps422.reclothes.ui.navigation.Screen
import com.c23ps422.reclothes.ui.screen.*
import com.c23ps422.reclothes.ui.screen.auth.Welcome
import com.c23ps422.reclothes.ui.screen.diy.DetailDIYScreen
import com.c23ps422.reclothes.ui.screen.auth.login.Login
import com.c23ps422.reclothes.ui.screen.auth.login.dataStore
import com.c23ps422.reclothes.ui.screen.medals.MedalsScreen
import com.c23ps422.reclothes.ui.screen.profile.UserScreen
import com.c23ps422.reclothes.ui.screen.profile.UserViewModel
import com.c23ps422.reclothes.ui.screen.auth.register.Register
import com.c23ps422.reclothes.ui.screen.home.HomeScreen
import com.c23ps422.reclothes.ui.screen.saleprocess.postToModel.ChooseImage
import com.c23ps422.reclothes.ui.screen.saleprocess.clothesIdentity.ClothesIdentity
import com.c23ps422.reclothes.ui.screen.saleprocess.createTransaction.DataAllClothesScreen
import com.c23ps422.reclothes.ui.screen.saleprocess.createTransaction.ListOfClothes
import com.c23ps422.reclothes.ui.screen.saleprocess.createTransaction.TransactionStatus
import com.c23ps422.reclothes.ui.screen.saleprocess.clothesIdentity.ClothesIdentityViewModel
import com.c23ps422.reclothes.ui.screen.saleprocess.createTransaction.CreateTransactionItemViewModel
import com.c23ps422.reclothes.ui.screen.saleprocess.createTransaction.CreateTransactionViewModel
import com.c23ps422.reclothes.ui.screen.saleprocess.createTransaction.GetTransactionViewModel
import com.c23ps422.reclothes.ui.screen.saleprocess.createTransaction.PriceRecommendation
import com.c23ps422.reclothes.ui.screen.saleprocess.createTransaction.UpdateTransactionViewModel
import com.c23ps422.reclothes.ui.screen.saleprocess.postToModel.DetectScreen
import com.c23ps422.reclothes.ui.screen.saleprocess.postToModel.PostToModelViewModel
import com.c23ps422.reclothes.ui.screen.saleprocess.postToModel.PreviewTakenImage
import com.c23ps422.reclothes.ui.screen.splash.SplashScreen
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
    var capturedImage by remember { mutableStateOf<File?>(null) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()

    val context = LocalContext.current

    val userViewModel: UserViewModel = viewModel(factory = UserViewModel.provideFactory(context))

    val createUserClothViewModel: CreateUserClothViewModel =
        viewModel(factory = CreateUserClothViewModel.provideFactory(context))

    val postToModelViewModel: PostToModelViewModel =
        viewModel(factory = PostToModelViewModel.provideFactory(context))

    val clothesIdentityViewModel: ClothesIdentityViewModel =
        viewModel(factory = ClothesIdentityViewModel.provideFactory(context))

    val createTransactionViewModel: CreateTransactionViewModel =
        viewModel(factory = CreateTransactionViewModel.provideFactory(context))

    val createTransactionItemViewModel: CreateTransactionItemViewModel =
        viewModel(factory = CreateTransactionItemViewModel.provideFactory(context))

    val updateTransactionViewModel: UpdateTransactionViewModel =
        viewModel(factory = UpdateTransactionViewModel.provideFactory(context))

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
        Screen.PreviewTakenImage.route,
        Screen.ClothesIdentity.route,
        Screen.ListOfClothes.route,
        Screen.TransactionStatus.route,
        Screen.PriceRecommendation.route
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
            SheetContent(
                pref = pref,
                navController = navController,
                userViewModel = userViewModel,
                createTransactionViewModel = createTransactionViewModel,
                createUserClothViewModel = createUserClothViewModel,
                onContinueClick = {
                    scope.launch {
                        sheetState.hide()
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
                    FloatingActionButton(
                        onClick = {
                            scope.launch {
                                if (sheetState.isVisible) {
                                    sheetState.hide()
                                } else {
                                    sheetState.show()
                                }
                            }
                        },
                        backgroundColor = Color(android.graphics.Color.parseColor("#27360B"))
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "fab",
                            tint = Color.White
                        )
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
                        pref = pref,
                        userViewModel = userViewModel,
                        navigateToDetail = { diyId ->
                            navController.navigate(Screen.DetailDIY.createRoute(diyId))
                        }
                    )
                }

                composable(Screen.UserScreen.route) {
                    UserScreen(
                        userViewModel = userViewModel,
                        navController = navController,
                        pref = pref
                    )
                }

                composable(Screen.Transaction.route) {
                    TransactionScreen()
                }

                composable(Screen.Medals.route) {
                    MedalsScreen()
                }

                composable(Screen.DataAllClothes.route) {
                    DataAllClothesScreen(
                        createTransactionViewModel = createTransactionViewModel,
                        updateTransactionViewModel = updateTransactionViewModel,
                        navController = navController
                    )
                }

                composable(Screen.PreviewTakenImage.route) {
                    PreviewTakenImage(
                        postToModelViewModel = postToModelViewModel,
                        navController = navController,
                        pref = pref,
                        photo = capturedImage,
                        onBackClick = {
                            navController.popBackStack()
                        },
                        onCancel = {
                            navController.navigate(Screen.Home.route)
                        }
                    )
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
                        onImageCaptured = { file ->
                            capturedImage = file
                            Handler(Looper.getMainLooper()).post {
                                navController.navigate(Screen.PreviewTakenImage.route)
                            }
                        },
                        onError = {

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

                composable(Screen.TransactionStatus.route) {
                    TransactionStatus(
                        updateTransactionViewModel = updateTransactionViewModel,
                        onBackPressed = {},
                        onBackHome = {
                            navController.navigate(Screen.Home.route)
                        }
                    )
                }

                composable(Screen.Login.route) {
                    Login(navController)
                }

                composable(Screen.Register.route) {
                    Register(navController)
                }

                composable(Screen.ClothesIdentity.route) {
                    ClothesIdentity(
                        clothesIdentityViewModel = clothesIdentityViewModel,
                        navController = navController,
                        postToModelViewModel = postToModelViewModel,
                        onCancel = {
                            navController.navigate(Screen.Home.route)
                        },

                        )
                }

                composable(Screen.ListOfClothes.route) {
                    ListOfClothes(
                        clothesIdentityViewModel = clothesIdentityViewModel,
                        navController = navController,
                        createTransactionViewModel = createTransactionViewModel,
                        createTransactionItemViewModel = createTransactionItemViewModel
                    )
                }

                composable(Screen.PriceRecommendation.route) {
                    PriceRecommendation(
                        navController = navController,
                        updateTransactionViewModel = updateTransactionViewModel
                    )
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
    pref: ReClothesPreference,
    navController: NavController,
    userViewModel: UserViewModel,
    createTransactionViewModel: CreateTransactionViewModel,
    createUserClothViewModel: CreateUserClothViewModel,
    onContinueClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val radioOptions = listOf(
        stringResource(R.string.rca_sc_one),
        stringResource(R.string.rca_sc_lot),
    )

    var radioStatus by remember {
        mutableStateOf(0)
    }

    var amountOfClothes by remember {
        mutableStateOf("")
    }

    var screen by remember {
        mutableStateOf("")
    }

    val userId by remember { mutableStateOf(userViewModel.userId) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(300.dp)
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(text = stringResource(R.string.rca_sc_title), fontSize = 24.sp)
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

        amountOfClothes =
            if (radioStatus == 0) stringResource(R.string.rca_sc_small) else stringResource(R.string.rca_sc_bulk)
        screen = if (radioStatus == 0) Screen.ChooseImage.route else Screen.DataAllClothes.route

        Spacer(modifier = Modifier.size(8.dp))
        ReButtonFullRounded(
            text = stringResource(R.string.rca_sc_continue),
            onClick = {
                onContinueClick()
                // Post Data
                createUserClothViewModel.createUserCloth(userId, amountOfClothes)
                if (radioStatus == 1){
                    createTransactionViewModel.createTransaction()
                }
            }
        )
    }

    val context = LocalContext.current
    createUserClothViewModel.uiState.collectAsState().value.let { uiState ->
        when (uiState) {
            is UiState.Idle -> {}

            is UiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            // When the UI state is success
            is UiState.Success -> {
                // Use LaunchedEffect to start a new coroutine which will survive recompositions.
                // When UiState changes, a new coroutine is launched (the old one is cancelled)
                LaunchedEffect(uiState) {
                    // Navigate to the specified screen.
                    // This code will only be executed once when the UiState becomes Success,
                    // regardless of how many times the composable gets recomposed.
                    pref.saveId(uiState.data.data.userClothes.id)
                    navController.navigate(screen)
                }
            }

            is UiState.Error -> {
                Toast.makeText(context, uiState.errorMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }
}