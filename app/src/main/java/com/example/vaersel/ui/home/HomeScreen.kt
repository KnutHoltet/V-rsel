package com.example.vaersel.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.vaersel.ui.home.animation.AnimationViewModel
import com.example.vaersel.ui.home.components.PullToRefreshColumn
import com.example.vaersel.ui.loading.LoadingScreen
import com.example.vaersel.ui.settings.SettingsScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel,
    settingsScreenViewModel: SettingsScreenViewModel,
    animationViewModel: AnimationViewModel,
    innerPadding: PaddingValues,
    onClickNavigate: (route: String) -> Unit,
    loadingSnackbar: @Composable () -> Unit,
    noDetailsSnackbar: @Composable () -> Unit,
    modifier: Modifier = Modifier
){

    // weather states
    // for the coming hours
    val weatherForTheNextHoursUiState by viewModel.weatherUiState.collectAsState()
    // for the coming weeks
    val weatherForTheWeekUiState by viewModel.weatherNextDaysUiState.collectAsState()

    // location state
    val locationWeatherUiState by viewModel.locationUiState.collectAsState()

    // ready state
    val readyUiState by viewModel.readyUiState.collectAsState()

    // snackbar state
    val snackbarHostState by viewModel.showSnackbarState.collectAsState()

    if (readyUiState) {
        if(snackbarHostState) {
            noDetailsSnackbar()
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            PullToRefreshColumn(
                viewModel = viewModel,
                settingsScreenViewModel = settingsScreenViewModel,
                animationViewModel = animationViewModel,
                weatherForTheNextHoursUiState = weatherForTheNextHoursUiState,
                locationWeatherUiState = locationWeatherUiState,
                weatherForTheWeekUiState = weatherForTheWeekUiState,
                onClickNavigate = onClickNavigate,
                innerPadding = innerPadding
            )
        }
    }
    else { // is active while the homeScreenViewModel is getting ready
        Column(
            modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
        ) {
            LoadingScreen(modifier)
            loadingSnackbar()
        }
    }
}