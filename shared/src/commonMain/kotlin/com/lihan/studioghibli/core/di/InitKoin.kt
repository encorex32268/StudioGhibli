package com.lihan.studioghibli.core.di

import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin() = initKoin {}

fun initKoin(config: KoinAppDeclaration = {}) {
    if (GlobalContext.getOrNull() == null) {
        startKoin {
            config()
            modules(
                platformModule,
                coreModule,
            )
        }
    }
}
