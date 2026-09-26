package com.lihan.studioghibli.core.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import org.jetbrains.compose.resources.vectorResource
import studioghibli.shared.generated.resources.Res
import studioghibli.shared.generated.resources.arrow_left
import studioghibli.shared.generated.resources.heart_fill
import studioghibli.shared.generated.resources.heart_outline

val HeartFilled: ImageVector
    @Composable
    get() = vectorResource(Res.drawable.heart_fill)


val HeartOutline: ImageVector
    @Composable
    get() = vectorResource(Res.drawable.heart_outline)

val ArrowLeft: ImageVector
    @Composable
    get() = vectorResource(Res.drawable.arrow_left)