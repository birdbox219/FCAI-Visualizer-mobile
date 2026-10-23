package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
  primary = TechIndigoLight,
  onPrimary = Color.Black,
  primaryContainer = TechIndigo,
  onPrimaryContainer = Color.White,
  secondary = TechCyan,
  onSecondary = Color.Black,
  tertiary = TechEmerald,
  onTertiary = Color.Black,
  background = DarkBg,
  onBackground = DarkTextPrimary,
  surface = DarkSurface,
  onSurface = DarkTextPrimary,
  surfaceVariant = DarkSurfaceCard,
  onSurfaceVariant = DarkTextSecondary,
  outline = DarkSurfaceCardBorder,
)

private val LightColorScheme = lightColorScheme(
  primary = TechIndigo,
  onPrimary = Color.White,
  primaryContainer = Color(0xFFE0E7FF),
  onPrimaryContainer = TechIndigo,
  secondary = TechCyan,
  onSecondary = Color.White,
  tertiary = TechEmerald,
  onTertiary = Color.White,
  background = LightBg,
  onBackground = LightTextPrimary,
  surface = LightSurface,
  onSurface = LightTextPrimary,
  surfaceVariant = LightSurfaceCard,
  onSurfaceVariant = LightTextSecondary,
  outline = Color(0xFFCBD5E1),
)

@Composable
fun FCAIVisualizerTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  content: @Composable () -> Unit,
) {
  val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

  MaterialTheme(
    colorScheme = colorScheme,
    typography = Typography,
    content = content
  )
}
