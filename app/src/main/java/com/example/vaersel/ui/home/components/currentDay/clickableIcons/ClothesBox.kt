package com.example.vaersel.ui.home.components.currentDay.clickableIcons

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.vaersel.R
import com.example.vaersel.ui.home.animation.AnimationViewModel
import com.example.vaersel.ui.home.animation.animationIconsMap

@Composable
fun ClothesBox(clothesText: String, animationViewModel: AnimationViewModel){
    Box(contentAlignment = Alignment.Center,modifier = Modifier
        .width(120.dp)
        .height(120.dp)
        .clickable { animationViewModel.openClothesPopUp() })
    {
        Column{
            Row(modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.Center) {
                val clothingIconId = animationIconsMap[clothesText] ?: R.drawable.fall_jacket_light
                Image(painter = painterResource(id = clothingIconId ), contentDescription = "Clothing Icon")
            }
            Row(modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.Center) {
                Text(text = "Antrekk")
            }
        }
    }
}