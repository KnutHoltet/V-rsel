package com.example.vaersel.ui.home.components.currentDay

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vaersel.R

@Composable
fun LocationText(locationTitle: String) {

    Row{

        // title text
        Text(
            text = locationTitle,
            modifier = Modifier
                .padding(start = 16.dp, end = 8.dp),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 32.sp
        )

        // location icon
        val locationIcon = R.drawable.near_me
        Icon(
            painter = painterResource(id = locationIcon),
            contentDescription = "Localized description",
            modifier = Modifier
                .paddingFromBaseline(8.dp),
            tint = MaterialTheme.colorScheme.onBackground
        )
    }
}