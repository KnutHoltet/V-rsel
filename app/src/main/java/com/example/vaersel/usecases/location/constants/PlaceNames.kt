package com.example.vaersel.usecases.location.constants


val places = hashMapOf(
    "Blindern" to "Universitetet i Oslo"
)

fun placeNames(placeNameToConvert: String) : String{
    return places[placeNameToConvert] ?: placeNameToConvert
}