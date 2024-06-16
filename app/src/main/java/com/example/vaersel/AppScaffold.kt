package com.example.vaersel

import android.content.pm.PackageManager
import androidx.activity.ComponentActivity
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.vaersel.data.location.UserLocation
import com.example.vaersel.hamburgerbar.menuList
import com.example.vaersel.ui.aboutUs.AboutUsScreen
import com.example.vaersel.ui.alerts.Alert
import com.example.vaersel.ui.alerts.AlertsViewModel
import com.example.vaersel.ui.home.animation.popupcards.AnimationPopUp
import com.example.vaersel.ui.home.animation.AnimationViewModel
import com.example.vaersel.ui.home.HomeScreen
import com.example.vaersel.ui.home.HomeScreenViewModel
import com.example.vaersel.ui.home.HourlyWeatherUiState
import com.example.vaersel.ui.hourly.HourlyScreen
import com.example.vaersel.ui.settings.SettingsScreen
import com.example.vaersel.ui.settings.SettingsScreenViewModel
import com.example.vaersel.ui.streak.StreakPopUp
import com.example.vaersel.ui.streak.StreakViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

const val ANIMATION_TIME_MS = 200

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScaffold(
    homeScreenViewModel: HomeScreenViewModel,
    settingsScreenViewModel: SettingsScreenViewModel,
    streakViewModel: StreakViewModel,
    animationViewModel: AnimationViewModel,
    alertsViewModel: AlertsViewModel,
    modifier: Modifier = Modifier,
    activity: ComponentActivity,
    location: UserLocation
) {

    // streak states
    val streakPopUpUiState by streakViewModel.streakPopUpUiState.collectAsState()
    
    // animation states
    val clothesPopUpUiState by animationViewModel.clothesPopUpUiState.collectAsState()
    val rainPopUpUiState by animationViewModel.rainPopUpUiState.collectAsState()
    val windPopUpUiState by animationViewModel.windPopUpUiState.collectAsState()

    // snackbar state
    val snackbarHostState = remember { SnackbarHostState() }

    // navigation and menu setup
    val navController = rememberNavController()
    val scope = rememberCoroutineScope()
    val menuState = rememberDrawerState(initialValue = DrawerValue.Closed)
    var selectedIndex by rememberSaveable {
        mutableIntStateOf(0)
    }

    // navigation menu function
    ModalNavigationDrawer(
        drawerContent = { ModalDrawerSheet(modifier = Modifier.width(180.dp)) {
            menuList().forEachIndexed{ index, hamburgerItem ->
                NavigationDrawerItem(
                    label = {Text(hamburgerItem.name)},
                    selected =  index == selectedIndex,
                    onClick = {
                        scope.launch { menuState.close()}
                        selectedIndex = index
                        when(index) {
                            0 -> navController.navigate("home")
                            1 -> navController.navigate("settings")
                            2 -> navController.navigate("about_us")
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = if(selectedIndex == index){hamburgerItem.filledIcon}
                            else{hamburgerItem.unfilledIcon},
                            contentDescription = hamburgerItem.name
                        )
                    }
                )
            }
        }},
    drawerState = menuState) {

        // this is the scaffold that surrounds the content of the app
        Scaffold(
            topBar = {
                TopAppBar(

                    // left in topappbar: menu icon / back from HourlyScreen arrow
                    navigationIcon = {
                        if (selectedIndex == -1) { // if in hourly screen
                            Row(modifier = Modifier.width(90.dp)) {
                                IconButton(
                                    onClick = {
                                        scope.launch {
                                            navController.navigate("home")
                                            selectedIndex = 0
                                        }
                                    }
                                ){
                                    Icon(Icons.AutoMirrored.Filled.ArrowBack, "go back")
                                }
                            }
                        }
                        else {
                            Row(modifier = Modifier.width(90.dp)) {
                                IconButton(
                                    onClick = {
                                        scope.launch {
                                            menuState.open()
                                        }
                                    }
                                ){
                                    Icon(Icons.Default.Menu, "open menu")
                                }
                            }
                        }
                    },

                    // middle of topappbar: alerts
                    title = {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                            Alert(alertsViewModel)
                        }
                    },

                    // right in topappbar: streak menu
                    actions = {
                        Row(modifier = Modifier.width(90.dp)) {
                            Button(
                                onClick = {
                                    streakViewModel.streakManager.recordDailyAction()
                                    streakViewModel.updateStreakUI()
                                    streakViewModel.openStreakPopUp()
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                                    contentColor = MaterialTheme.colorScheme.onBackground
                                ),
                                modifier = modifier
                                    .padding(end = 2.dp)
                            ) {
                                Text(
                                    text = "${ streakViewModel.currentStreak.collectAsState().value } ",
                                    style = MaterialTheme.typography.bodyLarge
                                )
                                Icon(
                                    imageVector = ImageVector.vectorResource(R.drawable.streak_flame),
                                    contentDescription = "Streak",
                                    Modifier.size(18.dp),
                                    tint = Color(0xFFE99F39)
                                )
                            }
                        }
                    }

                )
            },
            snackbarHost = {
                SnackbarHost (hostState = snackbarHostState)
            }
        )

        // the content inside the scaffold
        { innerPadding ->

            // the forecast for the coming days
            val weatherNextDaysUiState by homeScreenViewModel.weatherUiState.collectAsState()

            /*
            One pop-up can be shown at a time, and each one of them is administered
            by it's own state.
            */
            when {
                streakPopUpUiState ->
                    StreakPopUp(
                        onDismissRequest = { streakViewModel.closeStreakPopUp() },
                        currentStreak = streakViewModel.currentStreak.collectAsState().value,
                        streakRecord = streakViewModel.streakManager.getStreakRecordCount()
                    )

                clothesPopUpUiState ->
                    if (weatherNextDaysUiState is HourlyWeatherUiState.Success) {
                        AnimationPopUp(
                            id = "clothes",
                            weather = (weatherNextDaysUiState as HourlyWeatherUiState.Success).nextHours,
                            onDismissRequest = {animationViewModel.closeClothesPopUp()},
                            settingsScreenViewModel = settingsScreenViewModel,
                            animationViewModel = animationViewModel

                        )
                    }

                rainPopUpUiState ->
                    if (weatherNextDaysUiState is HourlyWeatherUiState.Success){
                        AnimationPopUp(
                            id = "rain",
                            weather = (weatherNextDaysUiState as HourlyWeatherUiState.Success).nextHours,
                            onDismissRequest = {animationViewModel.closeRainPopUp()},
                            settingsScreenViewModel = settingsScreenViewModel,
                            animationViewModel = animationViewModel
                        )
                    }

                windPopUpUiState ->
                    if (weatherNextDaysUiState is HourlyWeatherUiState.Success){
                        AnimationPopUp(
                            id = "wind",
                            weather = (weatherNextDaysUiState as HourlyWeatherUiState.Success).nextHours,
                            onDismissRequest = {animationViewModel.closeWindPopUp()},
                            settingsScreenViewModel = settingsScreenViewModel,
                            animationViewModel = animationViewModel
                        )
                    }
            }

            /*
            Navigation is done inside the scaffold to keep the same scaffold active across screens.
            The composable had to include the exitTransition argument to prevent multiple transitions
            happening at the same time
            */
            NavHost(navController = navController, startDestination = "home", modifier = Modifier.fillMaxSize()) {
                composable(
                    route = "home",
                    enterTransition = {
                        when (initialState.destination.route) {

                            "settings", "about_us" ->
                                slideIntoContainer(
                                    AnimatedContentTransitionScope.SlideDirection.Left,
                                    animationSpec = tween(ANIMATION_TIME_MS)
                                )

                            else -> null
                        }
                    }, exitTransition = { ExitTransition.None }
                ) {
                    HomeScreen(
                        homeScreenViewModel,
                        settingsScreenViewModel,
                        animationViewModel,
                        innerPadding,
                        {// navigation function
                            navController.navigate(it)
                            selectedIndex = -1
                        },
                        { //Snackbar showing if loading takes too long
                            LaunchedEffect(snackbarHostState) {
                                if (ContextCompat.checkSelfPermission(activity, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                                    delay(3000)
                                    val result = snackbarHostState.showSnackbar(
                                        message = "Loader den ikke? Sjekk internettforbindelsen din!",
                                        duration = SnackbarDuration.Indefinite,
                                        actionLabel = "Prøv igjen"
                                    )
                                    if (result == SnackbarResult.ActionPerformed) {
                                        //refresh data
                                        homeScreenViewModel.refresh()
                                        navController.navigate("home")

                                    }
                                }
                                else{
                                    delay(3000)
                                    val result = snackbarHostState.showSnackbar(
                                        message = "Du har ikke delt posisjon med oss, sjekk mobilinstillingene dine",
                                        duration = SnackbarDuration.Indefinite,
                                        actionLabel = "Sett lokasjon til Oslo"
                                    )
                                    if(result == SnackbarResult.ActionPerformed){
                                        // koordinater til Oslo
                                        location.lat = 59.944
                                        location.lon = 10.718
                                    }
                                }
                            }
                        },
                        { // Snackbar showing that detailed info for that day does not exist
                            LaunchedEffect(snackbarHostState) {
                                snackbarHostState.showSnackbar(
                                    message = "Det finnes ikke detaljert info for denne dagen ennå",
                                    duration = SnackbarDuration.Long,
                                )
                            }
                        }
                    )
                }

                composable(
                    route = "settings",
                    enterTransition = {
                        when (initialState.destination.route) {
                            "home", "about_us" ->
                                slideIntoContainer(
                                    AnimatedContentTransitionScope.SlideDirection.Left,
                                    animationSpec = tween(ANIMATION_TIME_MS)
                                )

                            else -> null
                        }
                    }, exitTransition = { ExitTransition.None }
                ) { SettingsScreen(viewModel = settingsScreenViewModel, innerPadding = innerPadding) }

                // sends id ar argument which is used to determined which day to show detailed info for
                composable(
                    "hourly/{id}", arguments = listOf(navArgument("id") {type = NavType.IntType}),
                    exitTransition = { ExitTransition.None}
                ) {
                    HourlyScreen(homeScreenViewModel = homeScreenViewModel, innerPadding, it.arguments?.getInt("id"))
                }

                composable(
                    route = "about_us",
                    enterTransition = {
                        when (initialState.destination.route) {
                            "home", "settings" ->
                                slideIntoContainer(
                                    AnimatedContentTransitionScope.SlideDirection.Left,
                                    animationSpec = tween(ANIMATION_TIME_MS)
                                )

                            else -> null
                        }
                    }, exitTransition = { ExitTransition.None }
                ){ AboutUsScreen(innerPadding = innerPadding) }
            }
        }
    }
}