package com.lihan.studioghibli.core.presentation.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


// === 核心中性主色調（非死白、非死黑、高級灰階） ===
val SoftWhiteBg = Color(0xFFF9F9FB)       // 淺色底：柔和霧白
val SoftWhiteSurface = Color(0xFFFFFFFF)  // 淺色卡片：純白層次
val NeutralDarkText = Color(0xFF1E2022)   // 淺色文字 / Primary

val SoftDarkBg = Color(0xFF141517)        // 深色底：深石墨灰（非死黑）
val SoftDarkSurface = Color(0xFF1F2024)   // 深色卡片：微透亮炭灰
val NeutralLightText = Color(0xFFE6E8EA)  // 深色文字 / Primary

// === 馬卡龍輔助色群（全部作為額外擴充） ===

val ColorScheme.MacaronPink: Color
    @Composable
    get() {
        return if (isSystemInDarkTheme()){
            MacaronPinkDark
        }else{
            MacaronPinkLight
        }
    }
val MacaronPinkLight get() = Color(0xFFF7A8B8)
val MacaronPinkDark get() = Color(0xFFD67B8E)

val MacaronYellow: Color
    @Composable
    get() {
        return if (isSystemInDarkTheme()){
            MacaronYellowDark
        }else{
            MacaronYellowLight
        }
    }

val MacaronYellowLight get() = Color(0xFFFBE285)
val MacaronYellowDark get() = Color(0xFFD6BC5C)

val ColorScheme.MacaronGreen: Color
    @Composable
    get() {
        return if (isSystemInDarkTheme()){
            MacaronGreenDark
        }else{
            MacaronGreenLight
        }
    }
val MacaronGreenLight get() = Color(0xFF98D7C2)
val MacaronGreenDark get() = Color(0xFF6FA895)

val MacaronBlue: Color
    @Composable
    get() {
        return if (isSystemInDarkTheme()){
            MacaronBlueDark
        }else{
            MacaronBlueLight
        }
    }
val MacaronBlueLight get() = Color(0xFF90CAF9)
val MacaronBlueDark get() = Color(0xFF6497C4)


val Gray: Color
    @Composable
    get() {
        return if (isSystemInDarkTheme()){
            Color.LightGray
        }else{
            Color.DarkGray
        }
    }