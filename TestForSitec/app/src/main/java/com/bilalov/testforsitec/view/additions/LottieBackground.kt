package com.bilalov.testforsitec.view.additions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.bilalov.testforsitec.R


@Composable
fun LottieBackground(
    isLoading: Boolean,
    content: @Composable () -> Unit,
) = if (isLoading
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.error))
    Box(
        Modifier
            .fillMaxSize(),
        Alignment.Center,

    ) {
        LottieAnimation(
            composition = composition,
            isPlaying = true,
            restartOnPlay = true,
            iterations = 1000000000,
            modifier = Modifier.fillMaxSize(),
        )
    }
} else {
    content()
}