package com.lihan.studioghibli.core.di

import com.lihan.studioghibli.core.data.local.RoomFilmLocalDataSource
import com.lihan.studioghibli.core.data.networking.HttpClientFactory
import com.lihan.studioghibli.core.data.remote.KtorFilmRemoteDataSource
import com.lihan.studioghibli.core.data.repository.OfflineFirstFilmRepository
import com.lihan.studioghibli.core.data.local.FilmDatabase
import com.lihan.studioghibli.core.data.local.dao.FilmDao
import com.lihan.studioghibli.core.data.local.getRoomDatabase
import com.lihan.studioghibli.core.domain.datasource.FilmLocalDataSource
import com.lihan.studioghibli.core.domain.datasource.FilmRemoteDataSource
import com.lihan.studioghibli.core.domain.repository.FilmRepository
import org.koin.core.module.Module
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val coreModule: Module = module {
    single { HttpClientFactory.create(get()) }
    single<FilmDatabase> { getRoomDatabase(builder = get()) }
    single<FilmDao> { get<FilmDatabase>().filmDao }

    // Data Sources
    singleOf(::KtorFilmRemoteDataSource) { bind<FilmRemoteDataSource>() }
    singleOf(::RoomFilmLocalDataSource) { bind<FilmLocalDataSource>() }

    // Repository
    singleOf(::OfflineFirstFilmRepository) { bind<FilmRepository>() }
}
