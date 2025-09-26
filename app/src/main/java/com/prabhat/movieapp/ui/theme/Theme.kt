package com.prabhat.movieapp.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/*private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40

    *//* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    *//*
)*/

private val DarkColorPalette = darkColorScheme(
    primary = ThemeColors.Night.primary,
    onPrimary = ThemeColors.Night.text,
    surface = ThemeColors.Night.surafce,
    background = ThemeColors.Night.bacground
)

private val LightColorPalette = lightColorScheme(
    primary = ThemeColors.Day.primary,
    onPrimary = ThemeColors.Day.text,
    surface = ThemeColors.Day.surafce,
    background = ThemeColors.Day.bacground
)

object MaterialTheme {
    val colorScheme: ColorScheme
        @Composable
        get() = androidx.compose.material3.MaterialTheme.colorScheme

    val typography: androidx.compose.material3.Typography
        @Composable
        get() = androidx.compose.material3.MaterialTheme.typography

    val shapes: androidx.compose.material3.Shapes
        @Composable
        get() = androidx.compose.material3.MaterialTheme.shapes

    // Example of a custom spacing object (not part of Material Design 3 by default)
    val spacing: Spacing
        @Composable
        get() = Spacing()
}

// Example Spacing object
data class Spacing(
    val extraSmall: Dp = 4.dp,
    val small: Dp = 8.dp,
    val medium: Dp = 16.dp,
    val large: Dp = 24.dp,
    val extraLarge: Dp = 32.dp
)

@Composable
fun MovieAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorPalette
        else -> LightColorPalette
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}