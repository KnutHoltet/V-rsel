package com.example.vaersel.ui.home.components.currentDay

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.vaersel.ui.home.HourlyWeatherUiState
import com.example.vaersel.ui.home.LocationWeatherUiState
import com.example.vaersel.ui.home.animation.AnimationIcons
import com.example.vaersel.ui.home.animation.AnimationViewModel
import com.example.vaersel.ui.settings.SettingsScreenViewModel

@Composable
fun HomeCard(weatherForTheNextHoursUiState: HourlyWeatherUiState, locationWeatherUiState: LocationWeatherUiState, settingsScreenViewModel: SettingsScreenViewModel, animationViewModel: AnimationViewModel) {

    if (weatherForTheNextHoursUiState is HourlyWeatherUiState.Success && locationWeatherUiState is LocationWeatherUiState.Success){

        /* Weather */
        val weatherEntries = weatherForTheNextHoursUiState.nextHours
        val temperatureNowString = weatherForTheNextHoursUiState.weatherNowString
        val weatherNow = weatherForTheNextHoursUiState.weatherNowEntry!!.data.instant.details

        /* Location */
        val location = locationWeatherUiState.location

        Column {
            Spacer(modifier = Modifier.height(12.dp))

            LocationText(location)
            TemperatureNowTitle(temperatureNowString, weatherEntries)
            FeelsLikeText(weatherNow.air_temperature, weatherNow.wind_speed)

            Spacer(modifier = Modifier.height(16.dp))

            HourlyTodayCard(weatherEntries)

            Spacer(modifier = Modifier.height(16.dp))

            AnimationIcons(weatherEntries, settingsScreenViewModel, animationViewModel)

            Spacer(modifier = Modifier.height(16.dp))
        }

    }
}