package com.lihan.studioghibli.core.presentation.ui

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = NeutralDarkText,
    onPrimary = SoftWhiteBg,
    background = SoftWhiteBg,
    onBackground = NeutralDarkText,
    surface = SoftWhiteSurface,
    onSurface = NeutralDarkText
)

private val DarkColorScheme = darkColorScheme(
    primary = NeutralLightText,
    onPrimary = SoftDarkBg,
    background = SoftDarkBg,
    onBackground = NeutralLightText,
    surface = SoftDarkSurface,
    onSurface = NeutralLightText
)

@Composable
fun GhibliTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        content = content,
        typography = getAppTypography()
    )
}