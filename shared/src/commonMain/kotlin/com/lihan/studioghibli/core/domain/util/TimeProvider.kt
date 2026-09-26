package com.lihan.studioghibli.core.domain.util

import kotlin.time.Clock

object TimeProvider {
    fun getCurrentTimeMillis(): Long{
        return Clock.System.now().toEpochMilliseconds()
    }
}

