package com.example.vaersel.ui.theme

import androidx.compose.material3.ColorScheme

import com.example.vaersel.ui.theme.spring.*
import com.example.vaersel.ui.theme.summer.*
import com.example.vaersel.ui.theme.fall.*
import com.example.vaersel.ui.theme.winter.*

enum class AllThemes(val theme: Theme) {
    ZERO(Theme(springLightScheme, springDarkScheme, springHighContrastLightColorScheme, springHighContrastDarkColorScheme, "Vår")),
    ONE(Theme(summerLightScheme, summerDarkScheme, summerHighContrastLightColorScheme, summerHighContrastDarkColorScheme, "Sommer")),
    TWO(Theme(fallLightScheme, fallDarkScheme, fallHighContrastLightColorScheme, fallHighContrastDarkColorScheme, "Høst")),
    THREE(Theme(winterLightScheme, winterDarkScheme, winterHighContrastLightColorScheme, winterHighContrastDarkColorScheme, "Vinter")),
}

data class Theme(
    val lightColorScheme: ColorScheme,
    val darkColorScheme: ColorScheme,
    val highContrastLightColorScheme: ColorScheme,
    val highContrastDarkColorScheme: ColorScheme,
    val themeName: String
)