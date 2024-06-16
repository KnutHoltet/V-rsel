package com.example.vaersel

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.vaersel.data.location.UserLocation
import com.example.vaersel.data.streak.StreakManager
import com.example.vaersel.ui.alerts.AlertsViewModel
import com.example.vaersel.ui.home.HomeScreenViewModel
import com.example.vaersel.ui.home.animation.AnimationViewModel
import com.example.vaersel.ui.settings.SettingsScreenViewModel
import com.example.vaersel.ui.streak.StreakViewModel
import com.example.vaersel.ui.theme.VaerselTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            /*
            Object used for storing to and fetching data from the device.
            Is used to save settings upon closing and opening app.
             */
            val sharedPref = this.getSharedPreferences("preferences", Context.MODE_PRIVATE)

            // SettingsScreenViewModel is initialized first because it is needed in HomeScreenViewModel
            val settingsScreenViewModel = SettingsScreenViewModel(sharedPref)
            settingsScreenViewModel.correctLightOrDarkMode(isSystemInDarkTheme())

            VaerselTheme(settingsScreenViewModel) {

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    // objects that require context
                    val userCoords = UserLocation(this)
                    val streakManager = StreakManager(this)

                    // viewmodel creation
                    val homeScreenViewModel = HomeScreenViewModel(userCoords)
                    val alertsViewModel = AlertsViewModel(userCoords)
                    val streakViewModel =  StreakViewModel(streakManager)
                    val animationViewModel = AnimationViewModel()

                    // initializes the scaffold that contains the entire app
                    AppScaffold(
                        homeScreenViewModel = homeScreenViewModel,
                        settingsScreenViewModel = settingsScreenViewModel,
                        streakViewModel = streakViewModel,
                        animationViewModel = animationViewModel,
                        alertsViewModel = alertsViewModel,
                        activity = this,
                        location = userCoords
                    )
                }
            }
        }
    }
}