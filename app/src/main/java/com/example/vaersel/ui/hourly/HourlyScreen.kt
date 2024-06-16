package com.example.vaersel.ui.hourly

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.vaersel.data.weather.icons.weatherIcons
import com.example.vaersel.ui.home.HomeScreenViewModel
import com.example.vaersel.ui.home.WeekForecastUiState
import com.example.vaersel.ui.logic.getDateString
import kotlin.math.roundToInt

@Composable
fun HourlyScreen(homeScreenViewModel: HomeScreenViewModel, innerpadding: PaddingValues, id: Int?) {

    // as detailed info as availible for the next week
    val weatherNextDaysUiState by homeScreenViewModel.weatherNextDaysUiState.collectAsState()

    if (weatherNextDaysUiState is WeekForecastUiState.Success) {

        // determines which date to show info for
        val day = when(id) {
            1 -> (weatherNextDaysUiState as WeekForecastUiState.Success).sevenNextDays[0]
            else -> (weatherNextDaysUiState as WeekForecastUiState.Success).sevenNextDays[1]
        }
        Column(
            modifier = Modifier.padding(innerpadding)
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.CenterHorizontally)
                    .padding(10.dp)
            ) {

                // date which data is showed for
                Text(
                    text = getDateString(day[0].time.subSequence(0, 10).toString()),
                    fontWeight = FontWeight.Bold
                )

            }

            Row(
                Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // titles
                Row(
                    Modifier
                        .width(100.dp)
                        .wrapContentWidth(Alignment.CenterHorizontally)
                ) {
                    Text("Tid")
                }
                Spacer(Modifier.width(100.dp))
                Row(
                    Modifier
                        .width(100.dp)
                        .wrapContentWidth(Alignment.CenterHorizontally)
                ) {
                    Text("Temperatur")
                }
            }

            LazyColumn{
                items(day) {timeSeriesEntry ->

                    Row(
                        Modifier
                            .fillMaxWidth()
                            .background(color = MaterialTheme.colorScheme.surfaceVariant)
                            .height(50.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        // shows which hour
                        Row(
                            Modifier
                                .width(50.dp)
                                .wrapContentWidth(Alignment.CenterHorizontally)
                        ) {

                            Text(timeSeriesEntry.time.subSequence(11, 16).toString())

                        }

                        Spacer(modifier = Modifier.width(60.dp))

                        // shows weather icon
                        val symbol = if(isSystemInDarkTheme()) {

                            if(timeSeriesEntry.data.next_1_hours.summary.symbol_code.isNotEmpty()) {
                                weatherIcons(timeSeriesEntry.data.next_1_hours.summary.symbol_code + "_dark")

                            } else{
                                weatherIcons(timeSeriesEntry.data.next_6_hours.summary.symbol_code + "_dark")
                            }

                        } else {
                            @Suppress("UNNECESSARY_SAFE_CALL")
                            weatherIcons(timeSeriesEntry.data.next_1_hours?.summary?.symbol_code?: timeSeriesEntry.data.next_6_hours.summary.symbol_code)
                        }


                        Image(
                            painter = painterResource(id = symbol),
                            contentDescription = "værikon",
                            modifier = Modifier.size(30.dp)
                        )

                        Spacer(modifier = Modifier.width(60.dp))

                        // shows temperature
                        Row(
                            Modifier
                                .width(50.dp)
                                .wrapContentWidth(Alignment.CenterHorizontally)
                        ) {

                            if (timeSeriesEntry.data.instant.details.air_temperature >= 0) { // if plus degrees

                                Text(
                                    text = "${timeSeriesEntry.data.instant.details.air_temperature.roundToInt()}°C",
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = Color(0xFFF82C3E)
                                )

                            } else { // if minus degrees

                                Text(
                                    text = "${timeSeriesEntry.data.instant.details.air_temperature.roundToInt()}°C",
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = Color(0xFF006EDB)
                                )

                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }
        }
    }
}