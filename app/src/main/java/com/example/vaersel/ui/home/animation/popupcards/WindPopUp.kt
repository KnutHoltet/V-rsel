package com.example.vaersel.ui.home.animation.popupcards

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.vaersel.R
import com.example.vaersel.model.TimeSeriesEntry
import com.example.vaersel.ui.home.animation.AnimationViewModel
import com.example.vaersel.ui.home.animation.GifImage
import com.example.vaersel.ui.home.animation.animationIconsMap
import com.example.vaersel.ui.home.animation.helpfunctions.windNext6Hours
import kotlin.math.roundToInt


@Composable
fun WindPopUp(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    weatherInfo: List<TimeSeriesEntry>,
    season: String,
    animationViewModel: AnimationViewModel,
    isDarkMode: Boolean
) {

    var windCode = "leaf_$season"
    var gifImage = season
    /* season_wind */

    windCode += if(isDarkMode) "_dark" else "_lighthighcon"


    val wind = windNext6Hours(weatherInfo)

    val windStrength = when {
        wind < 2.0 -> "no_wind" //Finn no greier på nettet så disse tallene kan forsvares
        wind < 7.0 -> "medium_wind"
        else -> "strong_wind"
    }

    gifImage += "_$windStrength"

    AlertDialog(
        icon = {

            val symbol = animationIconsMap[windCode] ?: R.drawable.fall_leaf
            Image(painter = painterResource(id = symbol ), contentDescription = "Clothing Icon")
        },
        title = {
            Text("Vind: ${wind.roundToInt()} m/s")
        },
        text = {
            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally){
                GifImage(gifImage,animationViewModel)
                Spacer(modifier = Modifier.height(10.dp))
                Text(description(windStrength), style =  MaterialTheme.typography.bodyLarge, textAlign = TextAlign.Center)
            }
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

private fun description(windStrength: String): String {
    return when(windStrength) {
        "no_wind" -> "Nesten vindstille! I dag så blåser det ikke så mye!"
        "medium_wind" -> "Det blåser litt, men ikke noe voldsomt!"
        else -> "I dag vil det blåse mye! Pass på sakene dine, kan blåse vekk!"
    }
}

