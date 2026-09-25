package com.lihan.studioghibli.core.di

import androidx.room.Room
import androidx.room.RoomDatabase
import com.lihan.studioghibli.core.data.local.FilmDatabase
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import kotlinx.cinterop.ExperimentalForeignApi
import org.koin.core.module.Module
import org.koin.dsl.module
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

@OptIn(ExperimentalForeignApi::class)
actual val platformModule: Module = module {
    single<HttpClientEngine> { Darwin.create() }
    single<RoomDatabase.Builder<FilmDatabase>> {
        val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null
        )
        val path = requireNotNull(documentDirectory?.path) { "Document directory not found" }
        val dbFilePath = "$path/films.db"
        Room.databaseBuilder<FilmDatabase>(
            name = dbFilePath
        )
    }
}
