package com.example.vaersel.ui.home.components.currentDay

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.vaersel.data.weather.icons.weatherIcons
import com.example.vaersel.model.TimeSeriesEntry
import kotlin.math.roundToInt

@Composable
fun HourlyTodayCard(
    liste: List<TimeSeriesEntry>
) {
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp)
    ) {

        Column (
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.background)
        ){

            // weather hour by hour
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(32.dp),
                modifier = Modifier
                    .background(color = MaterialTheme.colorScheme.background)

            ){

                items(liste) {timeSeriesEntry ->

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = MaterialTheme.colorScheme.background)
                            .padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        if(liste.isNotEmpty()) {

                            // time
                            Text(
                                text = formatTimeString(timeSeriesEntry.time),
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onBackground

                            )

                            val symbol = if(isSystemInDarkTheme()) {
                                weatherIcons(timeSeriesEntry.data.next_1_hours.summary.symbol_code + "_dark")

                            } else {
                                weatherIcons(timeSeriesEntry.data.next_1_hours.summary.symbol_code)
                            }


                            Image(
                                painter = painterResource(id = symbol),
                                contentDescription = "værikon",
                                modifier = Modifier
                                    .size(32.dp)
                            )

                            // temperature
                            Text(
                                text = "${timeSeriesEntry.data.instant.details.air_temperature.roundToInt()}°C",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }
                }
            }
        }
    }
}

/* Example: 2024-03-21T10:00:00Z */
fun formatTimeString(time: String): String =
    time.slice(11..15)
