package com.example.vaersel.ui.home.animation.helpfunctions

import androidx.compose.runtime.Composable
import com.example.vaersel.model.TimeSeriesEntry

/* Function builds string, used to get clothes-icons from AnimationIconsMap*/

@Composable
fun clothesText(season: String, liste: List<TimeSeriesEntry>, isDarkMode:Boolean, highContrastOn:Boolean, tempPref: Int):String{
    var text = ""

    /* Finding avg temperature for next 6 hours*/
    var avgTemp = 0.0
    for(x in 0..5){

        if(liste.isNotEmpty()) {
            avgTemp += feelsLikeTemp(liste[x].data.instant.details.air_temperature,liste[x].data.instant.details.wind_speed)
        }
    }

    avgTemp /= 6

    if(liste.isNotEmpty()) {
        if(liste[0].data.next_6_hours.details.precipitation_amount>0){
            if(isDarkMode && avgTemp>-1) {
                return "rainjacket_dark"
            }
            else if(avgTemp>-1) { return "rainjacket_light"}
        }
    }


    text += when{
        avgTemp > 20 + tempPref -> "tshirt_"
        avgTemp > 10 + tempPref -> "sweater_"
        else -> "jacket_"
    }
    text+=season
    text += if(isDarkMode && highContrastOn){
        "_highcondark"
    } else if(!isDarkMode && highContrastOn){
        "_highconlight"
    } else if(isDarkMode){
        "_dark"
    } else "_light"

    return text

}