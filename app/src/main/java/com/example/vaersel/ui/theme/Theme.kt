package com.example.vaersel.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.example.vaersel.ui.settings.SettingsScreenViewModel


@Composable
fun VaerselTheme(
    settingsScreenViewModel: SettingsScreenViewModel,
    darkTheme: Boolean = settingsScreenViewModel.isDarkMode.collectAsState().value,

    // Dynamic color is available on Android 12+
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        if (settingsScreenViewModel.highContrastOn.collectAsState().value) {
            settingsScreenViewModel.selectedTheme.collectAsState().value.highContrastDarkColorScheme
        }
        else {
            settingsScreenViewModel.selectedTheme.collectAsState().value.darkColorScheme
        }
    }
    else{
        if (settingsScreenViewModel.highContrastOn.collectAsState().value) {
            settingsScreenViewModel.selectedTheme.collectAsState().value.highContrastLightColorScheme
        }
        else{
            settingsScreenViewModel.selectedTheme.collectAsState().value.lightColorScheme
        }
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}