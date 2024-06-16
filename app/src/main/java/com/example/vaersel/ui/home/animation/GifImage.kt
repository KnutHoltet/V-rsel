package com.example.vaersel.ui.home.animation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.request.ImageRequest
import com.example.vaersel.ui.home.animation.helpfunctions.animationMap

@Composable
fun GifImage(animationCode: String, viewModel: AnimationViewModel) {
    when(viewModel.animationState.collectAsState().value){

        true -> // plays animation
            AsyncImage(model = ImageRequest.Builder(LocalContext.current)
                .data(animationMap[animationCode])
                .decoderFactory(GifDecoder.Factory())
                .build(), contentDescription = "gif",
                modifier = Modifier
                    .height(180.dp)
                    .width(360.dp)
                    .clickable { viewModel.animationStateChange() },
                contentScale = ContentScale.FillBounds)

        else -> // replaces animation with still image (pause)
            Box(modifier = Modifier
                .height(180.dp)
                .width(360.dp)
                .clickable { viewModel.animationStateChange() }
            ){
                Column(
                    Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.Center)
                ) {
                    Icon(Icons.Filled.PlayArrow, "play animation")
                }
            }
    }
}