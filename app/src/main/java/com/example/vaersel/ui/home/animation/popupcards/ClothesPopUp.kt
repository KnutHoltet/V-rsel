package com.example.vaersel.ui.home.animation.popupcards

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.vaersel.R
import com.example.vaersel.ui.home.animation.animationIconsMap
import com.example.vaersel.model.TimeSeriesEntry
import com.example.vaersel.ui.home.animation.helpfunctions.avgTemp
import com.example.vaersel.ui.home.animation.helpfunctions.clothesText
import com.example.vaersel.ui.home.animation.helpfunctions.feelsLikeTemp


@Composable
fun ClothesPopUp(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    weatherInfo: List<TimeSeriesEntry>,
    season: String,
    highCon: Boolean,
    isDarkMode: Boolean,
    tempPref: Int
){

    val clothing = clothesText(season, weatherInfo, isDarkMode, highCon, tempPref)


    AlertDialog(
        icon = {

            val symbol = animationIconsMap[clothing] ?: R.drawable.spring_jacket_light
            Image(painter = painterResource(id = symbol ), contentDescription = "Clothing Icon")
        },
        title = {
            Text(text = title(clothing))
        },
        text = {
            Text(text = description(weatherInfo, tempPref),style =  MaterialTheme.typography.bodyLarge, textAlign = TextAlign.Center)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
                TextButton(
                    onClick = {
                        onConfirmation()
                    }
                ) {
                    Text("Bekreft")
                }
            }
        },
        dismissButton = {},
        tonalElevation = 25.dp
    )
}

private fun description(weatherInfo: List<TimeSeriesEntry>, tempPref: Int): String {

    var description = ""
    if(weatherInfo.isNotEmpty()){

        var avgTemp = 0.0
        for(x in 0..5){
            avgTemp += feelsLikeTemp(weatherInfo[x].data.instant.details.air_temperature,weatherInfo[x].data.instant.details.wind_speed)
        }
        avgTemp /= 6


        val jacket = "jakke"
        val rain = weatherInfo[0].data.next_6_hours.details.precipitation_amount

        description += when{
            avgTemp > 20 + tempPref -> "Det vil føles varmt! Du kan kle deg med t-skjorte!"
            avgTemp > 10 + tempPref -> "I dag kan du ha genser!"
            else -> "Det føles kaldt, kle deg godt med $jacket!"
        }

        if(rain > 0 && avgTemp(weatherInfo)>-1) {
            description += " Husk regnjakke eller paraply også!"
        }
    }

    return description
}

private fun title(clothingType: String): String {
    val clothes = clothingType.split("_")

    return when(clothes[0]) {
        "jacket" -> "Antrekk: Jakke"
        "sweater" -> "Antrekk: Genser"
        "tshirt" -> "Antrekk: T-skjorte"
        else -> "Antrekk: Regnjakke"
    }
}

