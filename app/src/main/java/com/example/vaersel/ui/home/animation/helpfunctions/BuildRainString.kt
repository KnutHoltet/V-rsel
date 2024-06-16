package com.example.vaersel.ui.home.animation.helpfunctions

import com.example.vaersel.model.TimeSeriesEntry

/* Builds string, used to get rain-icons from AnimationIconsMap*/
fun rainText(liste: List<TimeSeriesEntry>, isDarkMode: Boolean):String{
    var result = ""

    if(liste.isNotEmpty()) {
        val rain = liste[0].data.next_6_hours.details.precipitation_amount

        if(avgTemp(liste)<-1){
            when{
                rain == 0.0 -> return "no_snow"
                rain <10 -> return "snow"
                else -> return "much_snow"
            }
        }
        result += when{
            rain == 0.0 -> "no_rain"
            rain < 5.0 -> "rain" //HIGHCoN IS FAKE? MAYBE FIX LATER
            else -> "much_rain"
        }

        result += if(isDarkMode) "_dark"
        else "_summer_lighthighcon"

        if(avgTemp(liste)<-1){
            result = "snowflake"
        }

    }
    return result
}