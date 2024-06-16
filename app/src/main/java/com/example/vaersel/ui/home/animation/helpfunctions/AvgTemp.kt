package com.example.vaersel.ui.home.animation.helpfunctions

import com.example.vaersel.model.TimeSeriesEntry

fun avgTemp(liste: List<TimeSeriesEntry>): Double {
    var temp = 0.0
    for(x in 0..5){
        temp += feelsLikeTemp( liste[x].data.instant.details.air_temperature,liste[x].data.instant.details.wind_speed )
    }
    temp /= 6
    return temp
}