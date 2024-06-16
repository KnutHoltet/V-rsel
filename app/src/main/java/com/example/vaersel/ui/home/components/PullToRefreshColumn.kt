package com.example.vaersel.ui.home.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import com.example.vaersel.ui.home.HomeScreenViewModel
import com.example.vaersel.ui.home.HourlyWeatherUiState
import com.example.vaersel.ui.home.LocationWeatherUiState
import com.example.vaersel.ui.home.WeekForecastUiState
import com.example.vaersel.ui.home.animation.AnimationViewModel
import com.example.vaersel.ui.home.components.currentDay.HomeCard
import com.example.vaersel.ui.home.components.nextDays.NextDaysTableCard
import com.example.vaersel.ui.settings.SettingsScreenViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PullToRefreshColumn(
    lazyListState: LazyListState = rememberLazyListState(),
    viewModel: HomeScreenViewModel,
    weatherForTheNextHoursUiState: HourlyWeatherUiState,
    locationWeatherUiState: LocationWeatherUiState,
    settingsScreenViewModel: SettingsScreenViewModel,
    animationViewModel: AnimationViewModel,
    weatherForTheWeekUiState: WeekForecastUiState,
    onClickNavigate: (route: String) -> Unit,
    innerPadding: PaddingValues
) {

    // refresh state
    val pullToRefreshState = rememberPullToRefreshState()

    // list containing the composable functions in the HomeScreen
    val composable = listOf<@Composable () -> Unit>(
        {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // current day's information
                HomeCard(
                    weatherForTheNextHoursUiState = weatherForTheNextHoursUiState,
                    locationWeatherUiState = locationWeatherUiState,
                    settingsScreenViewModel = settingsScreenViewModel,
                    animationViewModel = animationViewModel
                )
            }
        },
        {
            Spacer(Modifier.height(16.dp))
        },
        {
            // information for the coming week
            PrivateNextDaysTableCard(
                weatherForTheWeekUiState = weatherForTheWeekUiState,
                locationWeatherUiState = locationWeatherUiState,
                onClickNavigate = onClickNavigate,
                activateNoDetailsSnackbar = {viewModel.showSnackbar()},
                settingsScreenViewModel = settingsScreenViewModel
            )
        }
    )

    Box(
        modifier = Modifier
            .nestedScroll(pullToRefreshState.nestedScrollConnection)
    ) {
        LazyColumn(
            state = lazyListState,
            modifier = Modifier
                .padding(innerPadding)
        ) {

            // shows each of the composable functions in the list
            item{
                Column {
                    composable.forEach {
                        it()
                    }
                }
            }

        }

        /* controls refreshing */
        if(pullToRefreshState.isRefreshing) {
            LaunchedEffect(Unit){
                pullToRefreshState.startRefresh()
                delay(1000)
                viewModel.refresh()
                pullToRefreshState.endRefresh()
            }
        }

        PullToRefreshContainer(
            state = pullToRefreshState,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(innerPadding)
        )
    }
}

@Composable
private fun PrivateNextDaysTableCard(
    weatherForTheWeekUiState: WeekForecastUiState,
    locationWeatherUiState: LocationWeatherUiState,
    onClickNavigate: (route: String) -> Unit,
    activateNoDetailsSnackbar: () -> Unit,
    settingsScreenViewModel: SettingsScreenViewModel
) {

    if (weatherForTheWeekUiState is WeekForecastUiState.Success && locationWeatherUiState is LocationWeatherUiState.Success){
        /* Weather */
        val sevenNextDays = weatherForTheWeekUiState.sevenNextDays

        /* Time for time kort */
        NextDaysTableCard(sevenNextDays, onClickNavigate, activateNoDetailsSnackbar, settingsScreenViewModel)

    }
}
