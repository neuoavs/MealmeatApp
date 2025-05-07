package com.example.mealmeatapp.ui.theme


import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Colors
val Orange = Color(0xFFFF5722)
val Gold = Color(0xFFFFD700)
val White = Color(0xFFFFFFFF)
val Black = Color(0xFF000000)
val Gray = Color(0xFF888888)
val LightCream = Color(0xFFFFF9E6)
val DarkGreen = Color(0xFF2E7D32)
val LightGreen = Color(0xFFE8F5E9)
val DarkTeal = Color(0xFF00695C)
val DarkBlue = Color(0xFF0D47A1)


// Font Sizes
val TitleFontSize = 24.sp
val SubtitleFontSize = 16.sp
val ButtonFontSize = 16.sp
val BodyFontSize = 14.sp
val LargeTitleFontSize = 32.sp
val MediumTitleFontSize = 28.sp

// Typography
val AppTypography = Typography(
    headlineLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = LargeTitleFontSize
    ),
    headlineMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = MediumTitleFontSize
    ),
    headlineSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = TitleFontSize
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = SubtitleFontSize
    ),
    bodySmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = BodyFontSize
    ),
    labelLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = ButtonFontSize
    )
)

@Composable
fun MealtimeAppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        typography = AppTypography,
        content = content
    )
}