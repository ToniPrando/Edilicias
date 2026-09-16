package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme =
  darkColorScheme(
    primary = EdiliciasPink,
    onPrimary = Color.White,
    primaryContainer = EdiliciasPinkDark,
    onPrimaryContainer = Color.White,
    secondary = EdiliciasTeal,
    onSecondary = Color.White,
    secondaryContainer = EdiliciasTealDark,
    onSecondaryContainer = Color.White,
    tertiary = EdiliciasGold,
    background = Color(0xFF201310),
    surface = Color(0xFF2A1B16),
    onBackground = Color(0xFFFDECEF),
    onSurface = Color(0xFFFDECEF),
    surfaceVariant = Color(0xFF38231C),
    outline = Color(0xFF5A3C32),
  )

private val LightColorScheme =
  lightColorScheme(
    primary = EdiliciasPink,
    onPrimary = Color.White,
    primaryContainer = EdiliciasPinkContainer,
    onPrimaryContainer = EdiliciasOnPinkContainer,
    secondary = EdiliciasTeal,
    onSecondary = Color.White,
    secondaryContainer = EdiliciasTealContainer,
    onSecondaryContainer = EdiliciasOnTealContainer,
    tertiary = EdiliciasChocolate,
    onTertiary = Color.White,
    tertiaryContainer = EdiliciasChocolateContainer,
    onTertiaryContainer = EdiliciasOnChocolateContainer,
    background = EdiliciasCreamBg,
    surface = EdiliciasSurface,
    onBackground = EdiliciasTextPrimary,
    onSurface = EdiliciasTextPrimary,
    surfaceVariant = EdiliciasSurfaceVariant,
    onSurfaceVariant = EdiliciasTextSecondary,
    outline = EdiliciasOutline,
    outlineVariant = EdiliciasOutlineVariant,
  )

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  // Set to false by default to maintain Edilicias signature logo branding
  dynamicColor: Boolean = false,
  content: @Composable () -> Unit,
) {
  val colorScheme =
    when {
      dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
        val context = LocalContext.current
        if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
      }

      darkTheme -> DarkColorScheme
      else -> LightColorScheme
    }

  MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
}

