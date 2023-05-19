package com.flowyh.letmecook.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

private val DarkColorScheme = darkColorScheme(
  primary = Purple200,
  secondary = Teal200
)

private val LightColorScheme = lightColorScheme(
  primary = Purple500,
  secondary = Teal200
)

@Composable
fun LetMeCookTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
  val colors = if (darkTheme) {
    DarkColorScheme
  } else {
    LightColorScheme
  }

  CompositionLocalProvider(LocalSpacing provides Spacing()) {
    MaterialTheme(
      colorScheme = colors,
      typography = Typography,
      shapes = Shapes,
      content = content
    )
  }
}