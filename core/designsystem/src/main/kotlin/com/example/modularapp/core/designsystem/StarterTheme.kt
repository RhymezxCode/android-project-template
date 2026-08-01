@file:Suppress("MagicNumber")

package com.example.modularapp.core.designsystem

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors =
    lightColorScheme(
        primary = Color(0xFF315DA8),
        onPrimary = Color.White,
        secondary = Color(0xFF276B5A),
        background = Color(0xFFF9F9FF),
        surface = Color(0xFFF9F9FF),
    )

private val DarkColors =
    darkColorScheme(
        primary = Color(0xFFA9C7FF),
        secondary = Color(0xFF8DD7BF),
        background = Color(0xFF111318),
        surface = Color(0xFF111318),
    )

@Composable
fun StarterTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        content = content,
    )
}
