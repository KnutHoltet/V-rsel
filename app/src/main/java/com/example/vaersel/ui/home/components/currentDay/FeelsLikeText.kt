package com.example.vaersel.ui.home.components.currentDay

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.vaersel.ui.home.animation.helpfunctions.feelsLikeTemp
import kotlin.math.roundToInt

@Composable
fun FeelsLikeText(temp: Double, wind: Double) {
    Text(
        text = "Føles som ${feelsLikeTemp(temp, wind).roundToInt()}°C",
        modifier = Modifier.padding(horizontal = 20.dp),
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onBackground
    )
}