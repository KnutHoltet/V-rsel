package com.example.vaersel.ui.home.components.currentDay

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vaersel.data.weather.icons.weatherIcons
import com.example.vaersel.model.TimeSeriesEntry

@Composable
fun TemperatureNowTitle(
    temperature: String,
    timeSeriesEntryList: List<TimeSeriesEntry>
) {

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "${temperature}°C",
            modifier = Modifier
                .padding(start = 16.dp, end = 8.dp),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 32.sp
        )

        val weatherDataThisHour: TimeSeriesEntry
        if (timeSeriesEntryList.isNotEmpty()) {
            weatherDataThisHour = timeSeriesEntryList[0]
            val symbolId = weatherIcons(weatherDataThisHour.data.next_1_hours.summary.symbol_code)

            Image(
                painter = painterResource(id =symbolId ),
                contentDescription = "værikon",
                modifier = Modifier
                    .size(48.dp)
            )
        }
    }
}