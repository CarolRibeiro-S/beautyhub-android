package com.beautyhub.app.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val BeautyHubColorScheme = lightColorScheme(
    primary = MarromEscuro,
    secondary = MarromMedio,
    tertiary = Dourado,
    background = BrancoQuente,
    surface = BegeClaro,
    onPrimary = BrancoQuente,
    onSecondary = BrancoQuente,
    onBackground = MarromEscuro,
    onSurface = MarromEscuro
)

@Composable
fun BeautyHubTheme(content: @Composable () -> Unit) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = MarromEscuro.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }
    MaterialTheme(
        colorScheme = BeautyHubColorScheme,
        typography = Typography,
        content = content
    )
}