package com.example.vaersel.ui.home.animation.helpfunctions

import kotlin.math.pow

/* Finds out what temperature outside feels like using wind, same formula that Yr uses*/
fun feelsLikeTemp(temp: Double, wind: Double): Double{
    return 13.12+(0.6125*temp)-(11.37*(wind.pow(0.16)))+(0.3965*temp)*wind.pow(0.16)
}