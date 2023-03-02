package com.example.detoxrank.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.detoxrank.R


val DMSans = FontFamily(
    Font(R.font.dm_sans_regular),
    Font(R.font.dm_sans_bold),
    Font(R.font.dm_sans_medium)
)

val JosefinSans = FontFamily(
    Font(R.font.josefin_sans_bold),
    Font(R.font.josefin_sans_regular),
    Font(R.font.josefin_sans_thin)
)

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = DMSans,
        fontWeight = FontWeight.Normal,
        fontSize = 23.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.5.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = DMSans
    ),
    bodySmall = TextStyle(
        fontFamily = DMSans,
        fontStyle = FontStyle.Italic
    ),
    titleMedium = TextStyle(
        fontFamily = JosefinSans,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp
    ),
)