package com.lihan.studioghibli.core.di

import androidx.room.Room
import androidx.room.RoomDatabase
import com.lihan.studioghibli.core.data.local.FilmDatabase
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {
    single<HttpClientEngine> { OkHttp.create() }
    single<RoomDatabase.Builder<FilmDatabase>> {
        val context = androidContext()
        val dbFile = context.getDatabasePath("films.db")
        Room.databaseBuilder<FilmDatabase>(
            context = context,
            name = dbFile.absolutePath
        )
    }
}
