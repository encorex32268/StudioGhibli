package com.lihan.studioghibli.core.di

import com.lihan.studioghibli.film.di.filmModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.mp.KoinPlatform

fun initKoin() = initKoin {}

fun initKoin(config: KoinAppDeclaration = {}) {
    if (KoinPlatform.getKoinOrNull() == null) {
        startKoin {
            config()
            modules(
                platformModule,
                coreModule,
                filmModule,
            )
        }
    }
}
