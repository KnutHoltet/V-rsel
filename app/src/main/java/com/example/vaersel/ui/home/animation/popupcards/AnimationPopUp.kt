package com.example.vaersel.ui.home.animation.popupcards

import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.example.vaersel.model.TimeSeriesEntry
import com.example.vaersel.ui.home.animation.AnimationViewModel
import com.example.vaersel.ui.settings.SettingsScreenViewModel

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun AnimationPopUp (
    id: String,
    weather: List<TimeSeriesEntry>,
    onDismissRequest:() -> Unit,
    settingsScreenViewModel: SettingsScreenViewModel,
    animationViewModel: AnimationViewModel
){
    val theme = settingsScreenViewModel.selectedTheme.collectAsState().value
    val highcon = settingsScreenViewModel.highContrastOn.collectAsState()


    val season = when(theme.themeName){
        "Vinter" -> "winter"
        "Vår" -> "spring"
        "Sommer" -> "summer"
        else -> "fall"
    }


    BasicAlertDialog(onDismissRequest = {
        onDismissRequest()
    }
    ) {
        when (id) {
            "wind" -> WindPopUp(
                onDismissRequest = { onDismissRequest() },
                onConfirmation = { onDismissRequest() },
                weatherInfo = weather,
                season = season,
                isDarkMode = settingsScreenViewModel.isDarkMode.collectAsState().value,
                animationViewModel = animationViewModel
            )

            "rain" -> RainPopUp(
                onDismissRequest = { onDismissRequest() },
                onConfirmation = { onDismissRequest() },
                weatherInfo = weather,
                isDarkMode = settingsScreenViewModel.isDarkMode.collectAsState().value
            )

            "clothes" -> ClothesPopUp(
                onDismissRequest = { onDismissRequest() },
                onConfirmation = { onDismissRequest() },
                weatherInfo = weather,
                season = season,
                highCon = highcon.value,
                settingsScreenViewModel.isDarkMode.collectAsState().value,
                settingsScreenViewModel.tempPref.collectAsState().value
            )
        }

    }
}