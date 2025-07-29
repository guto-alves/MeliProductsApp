package com.example.meliproducts.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.meliproducts.R

val ProximaNova = FontFamily(
    Font(R.font.proximanova_regular),
    Font(R.font.proximanova_extrabold, FontWeight.ExtraBold),
    Font(R.font.proximanova_bold, FontWeight.Bold),
    Font(R.font.proximanova_boldit, FontWeight.Bold, FontStyle.Italic),
    Font(R.font.proximanova_light, FontWeight.Light),
    Font(R.font.proximanova_black, FontWeight.Black),
)

val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = ProximaNova,
        fontWeight = FontWeight.Normal,
        fontSize = 36.sp
    ),
    displayMedium = TextStyle(
        fontFamily = ProximaNova,
        fontWeight = FontWeight.Normal,
        fontSize = 28.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = ProximaNova,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp,
    ),
    titleLarge = TextStyle(
        fontFamily = ProximaNova,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
//        lineHeight = 28.sp,
//        letterSpacing = 0.sp
    ),
    titleMedium = TextStyle(
        fontFamily = ProximaNova,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
    ),
    bodyLarge = TextStyle(
        fontFamily = ProximaNova,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
//        lineHeight = 24.sp,
//        letterSpacing = 0.5.sp
    ),
    labelSmall = TextStyle(
        fontFamily = ProximaNova,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
//        lineHeight = 16.sp,
//        letterSpacing = 0.5.sp
    )
)