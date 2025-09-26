package com.prabhat.movieapp.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.core.graphics.toColorInt

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val darkGray = Color(0xFF1a1717)
val darkBlue = Color(0xFF18192b)
val white = Color(0xFFF3F3F3)
val gray = Color(0xFF3F3F3F)
val blueGray = Color(0xFF404352)
val nightDark = Color(0xFF403757)

val purple = Color(0xFF9b11ba)
val orange = Color(0xFFdb660d)
val redOrange = Color(0xFFe84a23)
val green = Color(0xFF0ddb25)
val blue = Color(0xFF140ddb)
val brightBlue = Color(0xFF027cf5)


val Blur = Color(0xFF8D7857).copy(alpha = 0.5f)


val GreyLight = Color(0xFFB5B6BA)
val GreyDark = Color(0xFF60626C)


var red = Color(0xFFED1B24)


// define your colors for dark theme
val clear_dark = Color(0xFFA05162)
val dark_btn = Color(0xFF222427)

// define your colors for dark theme
val light_btn = Color("#E9F0F4".toColorInt())
val light_bg = Color("#F6F8F9".toColorInt())
val clear_light = Color(0xFFF1C8D1)
sealed class ThemeColors(
    val bacground: Color,
    val surafce: Color,
    val primary: Color,
    val text: Color
)  {
    object Night: ThemeColors(
        bacground = Color.Black,
        surafce = dark_btn,
        primary = clear_dark,
        text = Color.White
    )
    object Day: ThemeColors(
        bacground = light_bg,
        surafce = light_btn,
        primary = clear_light,
        text = Color.Black
    )
}