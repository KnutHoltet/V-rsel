package com.example.vaersel.ui.home.animation.helpfunctions

import com.example.vaersel.model.TimeSeriesEntry

/* Help function to figure out avg wind speed for next 6 hours*/
fun windNext6Hours(liste: List<TimeSeriesEntry>): Double {
    var vind = 0.0
    if (liste.isNotEmpty()) {
        for(x in 0..5){
            vind += liste[x].data.instant.details.wind_speed
        }

        vind /= 6
    }
    return vind
}