package com.example.myapplication.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColors = darkColorScheme(
    background = BgDark,
    surface = CardBg,
    primary = PurplePrimary,
    secondary = PurpleLight,
    onBackground = TextPrimary,
    onSurface = TextPrimary,
    onPrimary = Color.White
)

@Composable
fun SoundAppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = DarkColors,
        content = content
    )
}
