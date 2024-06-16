package com.example.vaersel.ui.home.animation


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.example.vaersel.model.TimeSeriesEntry
import com.example.vaersel.ui.home.animation.helpfunctions.clothesText
import com.example.vaersel.ui.home.animation.helpfunctions.rainText
import com.example.vaersel.ui.home.animation.helpfunctions.windText
import com.example.vaersel.ui.home.components.currentDay.clickableIcons.ClothesBox
import com.example.vaersel.ui.home.components.currentDay.clickableIcons.RainBox
import com.example.vaersel.ui.home.components.currentDay.clickableIcons.WindBox
import com.example.vaersel.ui.settings.SettingsScreenViewModel

@Composable
fun AnimationIcons(liste: List<TimeSeriesEntry>,viewmodel: SettingsScreenViewModel, animationViewModel: AnimationViewModel) {

    /* States */
    val highcontrast = viewmodel.highContrastOn.collectAsState().value
    val isDarkTheme = viewmodel.isDarkMode.collectAsState().value
    val theme = viewmodel.selectedTheme.collectAsState().value

    /* Building string to get icon from AnimationIconsMap */
    val season = when(theme.themeName){
        "Vinter" -> "winter"
        "Vår" -> "spring"
        "Sommer" -> "summer"
        else -> "fall"
    }
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ){
        Column {
            RainBox(rainText(liste, isDarkTheme), animationViewModel)
        }
        Column {
            ClothesBox(clothesText(season,liste,isDarkTheme,highcontrast, viewmodel.tempPref.collectAsState().value),animationViewModel)
        }
        Column {
            WindBox(windText(season,isDarkTheme,highcontrast),animationViewModel)
        }
    }
}



