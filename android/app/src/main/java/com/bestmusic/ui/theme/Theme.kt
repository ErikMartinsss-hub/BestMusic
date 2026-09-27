package com.bestmusic.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

private val SpotifyGreen = Color(0xFF1DB954)
private val SpotifyGreenLight = Color(0xFF1ED760)
private val DarkBackground = Color(0xFF121212)
private val DarkSurface = Color(0xFF1D1D1D)
private val DarkCard = Color(0xFF252525)
private val DarkDivider = Color(0xFF3A3A3A)

private val DarkColors = darkColorScheme(
    primary = SpotifyGreen,
    primaryContainer = Color(0xFF0A4D22),
    secondary = SpotifyGreenLight,
    secondaryContainer = Color(0xFF053D15),
    tertiary = Color(0xFFFFFFFF),
    background = DarkBackground,
    surface = DarkSurface,
    surfaceVariant = DarkCard,
    outline = DarkDivider,
    outlineVariant = DarkDivider.copy(alpha = 0.12f),
    onPrimary = Color.Black,
    onPrimaryContainer = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.White,
    onSurface = Color.White,
    onSurfaceVariant = Color(0xFFB3B3B3),
    inverseSurface = Color(0xFFE1E1E1),
    inverseOnSurface = Color.Black,
    inversePrimary = SpotifyGreen,
)

private val LightColors = lightColorScheme(
    primary = Color(0xFF0F9D4A),
    primaryContainer = Color(0xFFD8F9E3),
    secondary = Color(0xFF018786),
    secondaryContainer = Color(0xFFC8FAFA),
    background = Color(0xFFFFFFFF),
    surface = Color(0xFFF5F5F5),
    surfaceVariant = Color(0xFFE0E0E0),
    onPrimary = Color.White,
    onSurface = Color(0xFF121212),
    onSurfaceVariant = Color(0xFF555555),
)

private val Typography = Typography(
    displayLarge = TextStyle(fontFamily = FontFamily.Default, fontWeight = FontWeight.Bold, fontSize = 57.sp, letterSpacing = -0.25.sp, lineHeight = 64.sp),
    displayMedium = TextStyle(fontFamily = FontFamily.Default, fontWeight = FontWeight.Bold, fontSize = 45.sp, letterSpacing = 0.sp, lineHeight = 52.sp),
    displaySmall = TextStyle(fontFamily = FontFamily.Default, fontWeight = FontWeight.Bold, fontSize = 36.sp, letterSpacing = 0.sp, lineHeight = 44.sp),
    headlineLarge = TextStyle(fontFamily = FontFamily.Default, fontWeight = FontWeight.Bold, fontSize = 32.sp, letterSpacing = 0.sp, lineHeight = 40.sp),
    headlineMedium = TextStyle(fontFamily = FontFamily.Default, fontWeight = FontWeight.Bold, fontSize = 28.sp, letterSpacing = 0.sp, lineHeight = 36.sp),
    headlineSmall = TextStyle(fontFamily = FontFamily.Default, fontWeight = FontWeight.Bold, fontSize = 24.sp, letterSpacing = 0.sp, lineHeight = 32.sp),
    titleLarge = TextStyle(fontFamily = FontFamily.Default, fontWeight = FontWeight.Bold, fontSize = 22.sp, letterSpacing = 0.sp, lineHeight = 28.sp),
    titleMedium = TextStyle(fontFamily = FontFamily.Default, fontWeight = FontWeight.Medium, fontSize = 16.sp, letterSpacing = 0.15.sp, lineHeight = 24.sp),
    titleSmall = TextStyle(fontFamily = FontFamily.Default, fontWeight = FontWeight.Medium, fontSize = 14.sp, letterSpacing = 0.1.sp, lineHeight = 20.sp),
    bodyLarge = TextStyle(fontFamily = FontFamily.Default, fontWeight = FontWeight.Normal, fontSize = 16.sp, letterSpacing = 0.5.sp, lineHeight = 24.sp),
    bodyMedium = TextStyle(fontFamily = FontFamily.Default, fontWeight = FontWeight.Normal, fontSize = 14.sp, letterSpacing = 0.25.sp, lineHeight = 20.sp),
    bodySmall = TextStyle(fontFamily = FontFamily.Default, fontWeight = FontWeight.Normal, fontSize = 12.sp, letterSpacing = 0.4.sp, lineHeight = 16.sp),
    labelLarge = TextStyle(fontFamily = FontFamily.Default, fontWeight = FontWeight.Medium, fontSize = 14.sp, letterSpacing = 0.1.sp, lineHeight = 20.sp),
    labelMedium = TextStyle(fontFamily = FontFamily.Default, fontWeight = FontWeight.Medium, fontSize = 12.sp, letterSpacing = 0.5.sp, lineHeight = 16.sp),
    labelSmall = TextStyle(fontFamily = FontFamily.Default, fontWeight = FontWeight.Medium, fontSize = 11.sp, letterSpacing = 0.5.sp, lineHeight = 16.sp),
)

@Composable
fun BestMusicTheme(
    darkTheme: Boolean = true,
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        typography = Typography,
        content = content,
    )
}