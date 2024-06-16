package com.example.vaersel.ui.home.components.nextDays

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.vaersel.data.weather.icons.weatherIcons
import com.example.vaersel.model.TimeSeriesEntry
import com.example.vaersel.ui.home.components.currentDay.formatTimeString
import kotlin.math.roundToInt

@Composable
fun TableDayCard(
    infoForDay: List<TimeSeriesEntry>,
    dato: String,
    onClickNavigate: () -> Unit
) {
    Column {
        Text(
            text = dato,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
            fontWeight = FontWeight.Bold
        )
        Card(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .clickable { onClickNavigate() }
        ) {
            Column(
                Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    userScrollEnabled = false
                ) {
                    items(infoForDay.filter { // only keeps 4 entries, used for summarized info
                        it.time.subSequence(11, 13) == "00" ||
                                it.time.subSequence(11, 13) == "06" ||
                                it.time.subSequence(11, 13) == "12" ||
                                it.time.subSequence(11, 13) == "18"

                    }) { timeSeriesEntry ->
                        if (infoForDay.isNotEmpty()) {
                            Column(
                                modifier = Modifier
                                    .padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {

                                // hour
                                Text(
                                    text = formatTimeString(timeSeriesEntry.time),
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurface
                                )

                                // weather icon
                                val symbol = weatherIcons(timeSeriesEntry.data.next_6_hours.summary.symbol_code)
                                Image(
                                    painter = painterResource(id = symbol),
                                    contentDescription = "værikon",
                                    modifier = Modifier
                                        .size(32.dp)
                                )

                                // temperature
                                if (timeSeriesEntry.data.instant.details.air_temperature >= 0) {

                                    Text(
                                        text = "${timeSeriesEntry.data.instant.details.air_temperature.roundToInt()}°C",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = Color(0xFFF82C3E)
                                    )

                                } else {

                                    Text(
                                        text = "${timeSeriesEntry.data.instant.details.air_temperature}°C",
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = Color(0xFF006EDB)
                                    )

                                }
                            }
                        }
                    }
                }
            }
        }
    }
}