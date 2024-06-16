package com.example.vaersel.ui.home.components.nextDays


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.vaersel.data.weather.icons.weatherIcons
import com.example.vaersel.model.TimeSeriesEntry
import com.example.vaersel.ui.home.components.currentDay.formatTimeString
import com.example.vaersel.ui.logic.getDateString
import com.example.vaersel.ui.settings.SettingsScreenViewModel
import kotlin.math.roundToInt


/* Dette er for dag en */
@Composable
fun NextDaysTableCard(
    nextSevenDays: List<List<TimeSeriesEntry>>,
    onClickNavigate: (route: String) -> Unit,
    activateNoDetailsSnackbar: () -> Unit,
    settingsScreenViewModel: SettingsScreenViewModel
) {
    var teller = 0
    nextSevenDays.forEach {

        val date = it[0].time.subSequence(0, 10).toString()


        when (teller) {
            0 -> {
                TableDayCard(infoForDay = it, dato = "${getDateString(date)} ( i morgen )", onClickNavigate = { onClickNavigate("hourly/1") }, settingsScreenViewModel)
            }
            1 -> {
                TableDayCard(infoForDay = it, dato = getDateString(date), onClickNavigate = { onClickNavigate("hourly/2") }, settingsScreenViewModel)
            }
            else -> {
                TableDayCard(infoForDay = it, dato = getDateString(date), onClickNavigate = { activateNoDetailsSnackbar() }, settingsScreenViewModel)
            }
        }
        teller++
        Spacer(Modifier.height(16.dp))
    }
}

@Composable
fun TableDayCard(
    infoForDay: List<TimeSeriesEntry>,
    dato: String,
    onClickNavigate: () -> Unit,
    settingsScreenViewModel: SettingsScreenViewModel
) {
    val isDarkMode = settingsScreenViewModel.isDarkMode.collectAsState().value
    val blue = if(isDarkMode){
        Color(0xFF00B8F1)
    }
    else{Color(0xFF006EDB)}
    val red = if(isDarkMode){
        Color(0xFFFF2D3F)
    }
    else{
        Color(0xFFC60000)
    }

    Column {
        Text(
            text = dato,
            style = MaterialTheme.typography.bodyLarge, //riktig størrelse?
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
                    items(infoForDay.filter {
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

                                Text(
                                    text = formatTimeString(timeSeriesEntry.time),
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurface
                                )



                                val symbol = if(isSystemInDarkTheme()) {
                                    weatherIcons(timeSeriesEntry.data.next_6_hours.summary.symbol_code + "_dark")
                                } else {
                                    weatherIcons(timeSeriesEntry.data.next_6_hours.summary.symbol_code)
                                }

                                Image(
                                    painter = painterResource(id = symbol),
                                    contentDescription = "værikon",
                                    modifier = Modifier
                                        .size(32.dp)
                                )

                                if (timeSeriesEntry.data.instant.details.air_temperature >= 0) {

                                    Text(
                                        text = "${timeSeriesEntry.data.instant.details.air_temperature.roundToInt()}°C",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = red
                                    )

                                } else {

                                    Text(
                                        text = "${timeSeriesEntry.data.instant.details.air_temperature}°C",
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = blue
                                    )

                                }
                                //TODO: AVERAGE AV TEMPERATURER
                            }
                        }
                    }
                }
            }
        }
    }
}