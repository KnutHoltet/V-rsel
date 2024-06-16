package com.example.vaersel.ui.aboutUs

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.vaersel.R


@Composable
fun AboutUsScreen(innerPadding: PaddingValues) {
    Column(
        Modifier
            .padding(innerPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(Modifier.height(16.dp))

        Image(
            painter = painterResource(id = R.drawable.group_photo),
            contentDescription = "Bilde av oss som har laget appen",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(200.dp)
                .clip(CircleShape)
        )

        Spacer(Modifier.height(16.dp))

        Text(
            text = "Værsel",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(Modifier.height(16.dp))

        Text(
            text = "Vi er et team på seks personer som i løpet av 11 uker " +
                    "har jobbet sammen på denne appen. Vi har hatt faget IN2000 på " +
                    "Universitetet i Oslo, som handler om prosjektarbeid og " +
                    "android-utvikling, og vi valgte å utvikle en vær-app for " +
                    "barn i ungdomsskole-alderen.\n\nVi håper du liker den!",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}