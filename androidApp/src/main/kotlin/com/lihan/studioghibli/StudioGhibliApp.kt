package com.lihan.studioghibli

import android.app.Application
import com.lihan.studioghibli.core.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class StudioGhibliApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidLogger()
            androidContext(this@StudioGhibliApp)
        }
    }
}
