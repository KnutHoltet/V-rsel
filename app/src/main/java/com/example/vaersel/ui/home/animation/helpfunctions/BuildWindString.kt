package com.example.vaersel.ui.home.animation.helpfunctions

/* Builds string, used to get wind-icons from AnimationIconsMap*/
fun windText(season: String, isDarkMode: Boolean, highcon: Boolean):String{
    var text = ""
    if(isDarkMode) {
        text += "leaf_${season}_dark"
    }
    else{ text += "leaf_${season}"}

    if(highcon && !isDarkMode){
        text += "_lighthighcon"
    }
    else if(highcon && isDarkMode){
        text += "highcon"
    }
    return text
}