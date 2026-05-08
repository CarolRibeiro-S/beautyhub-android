package com.beautyhub.app.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.beautyhub.app.R

val FrauncesFontFamily = FontFamily(
    Font(R.font.fraunces, FontWeight.SemiBold)
)

val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = FrauncesFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 26.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = FrauncesFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp
    )
)