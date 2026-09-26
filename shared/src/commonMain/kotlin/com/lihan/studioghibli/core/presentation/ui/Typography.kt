package com.lihan.studioghibli.core.presentation.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalLocale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.Font
import studioghibli.shared.generated.resources.GoogleSans_Bold
import studioghibli.shared.generated.resources.GoogleSans_Medium
import studioghibli.shared.generated.resources.GoogleSans_Regular
import studioghibli.shared.generated.resources.GoogleSans_SemiBold
import studioghibli.shared.generated.resources.NotoSansJP_Bold
import studioghibli.shared.generated.resources.NotoSansJP_Medium
import studioghibli.shared.generated.resources.NotoSansJP_Regular
import studioghibli.shared.generated.resources.NotoSansJP_SemiBold
import studioghibli.shared.generated.resources.Res

@Composable
fun getAppFontFamily(): FontFamily {
    val japaneseFont = FontFamily(
        Font(Res.font.NotoSansJP_Bold, FontWeight.Bold),
        Font(Res.font.NotoSansJP_Medium, FontWeight.Medium),
        Font(Res.font.NotoSansJP_Regular, FontWeight.Normal),
        Font(Res.font.NotoSansJP_SemiBold, FontWeight.SemiBold)
    )

    val englishFont = FontFamily(
        Font(Res.font.GoogleSans_Bold, FontWeight.Bold),
        Font(Res.font.GoogleSans_Medium, FontWeight.Medium),
        Font(Res.font.GoogleSans_Regular, FontWeight.Normal),
        Font(Res.font.GoogleSans_SemiBold, FontWeight.SemiBold)
    )
    val locale = LocalLocale.current
    return if (locale == Locale("JAPANESE")){
        japaneseFont
    }else{
        englishFont
    }
}

@Composable
fun getAppTypography(): Typography {
    val fontFamily = getAppFontFamily()

    return Typography(
        titleLarge = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            lineHeight = 28.sp
        ),
        titleMedium = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 18.sp,
            lineHeight = 24.sp
        ),
        titleSmall = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            lineHeight = 20.sp
        ),
        bodyLarge = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.5.sp
        ),
        bodyMedium = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            letterSpacing = 0.25.sp
        ),
        bodySmall = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            lineHeight = 16.sp,
            letterSpacing = 0.25.sp
        ),
        labelLarge = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            lineHeight = 24.sp
        ),
        labelMedium = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            lineHeight = 16.sp
        ),
        labelSmall = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
            lineHeight = 14.sp
        ),
    )
}