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
import com.example.vaersel.ui.home.animation.helpfunctions.rainText

@Composable
fun RainPopUp(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    weatherInfo: List<TimeSeriesEntry>,
    isDarkMode: Boolean
) {

    val rainCode = rainText(weatherInfo, isDarkMode)

    AlertDialog(
        icon = {
            val symbol = animationIconsMap[rainCode] ?: R.drawable.no_rain
            Image(painter = painterResource(id = symbol ), contentDescription = "Clothing Icon")
        },
        title = {
            Text(text = title(rainCode))
        },
        text = {
            Text(text = description(rainCode, weatherInfo),style =  MaterialTheme.typography.bodyLarge, textAlign = TextAlign.Center)
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


private fun description(rainCode: String, liste: List<TimeSeriesEntry>): String {
    if(avgTemp(liste)<-1){
        return when{
            "no_snow" in rainCode -> "I dag er det ikke meldt snø men det er fortsatt kaldt ute!"
            "much_snow" in rainCode -> "I dag er det meldt mye snø, mye mer enn vanlig!"
            else -> "I dag er det meldt litt snø og det kan være glatt ute!"
        }
    }
    else {
        return when {
            "no_rain" in rainCode -> "Gode nyheter! Det skal ikke regne i dag!"
            "much_rain" in rainCode -> "I dag er det meldt mer regn enn til vanlig og det kommer til å bli vått ute. Ta med en paraply!"
            else -> "I dag er det meldt litt regn, og det kan være lurt å ta med paraply!"
        }
    }
}

private fun title(rainCode: String): String {
    return when {
        "snow" in rainCode -> "Nedbør: Snø!"
        "no_rain" in rainCode -> "Nedbør: Ingenting"
        "much_rain" in rainCode-> "Nedbør: Mye!"
        else -> "Nedbør: Litte gran"
    }
}

