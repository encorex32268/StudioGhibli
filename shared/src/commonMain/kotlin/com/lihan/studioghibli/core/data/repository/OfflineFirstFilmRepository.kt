package com.lihan.studioghibli.core.data.repository

import com.lihan.studioghibli.core.domain.datasource.FilmLocalDataSource
import com.lihan.studioghibli.core.domain.datasource.FilmRemoteDataSource
import com.lihan.studioghibli.core.domain.model.Film
import com.lihan.studioghibli.core.domain.repository.FilmRepository
import com.lihan.studioghibli.core.domain.util.DataError
import com.lihan.studioghibli.core.domain.util.EmptyResult
import com.lihan.studioghibli.core.domain.util.asEmptyDataResult
import com.lihan.studioghibli.core.domain.util.onSuccess
import kotlinx.coroutines.flow.Flow

class OfflineFirstFilmRepository(
    private val remoteDataSource: FilmRemoteDataSource,
    private val localDataSource: FilmLocalDataSource
) : FilmRepository {

    override fun getFilms(): Flow<List<Film>> {
        return localDataSource.getFilms()
    }

    override fun getFilmById(id: String): Flow<Film?> {
        return localDataSource.getFilmById(id)
    }

    override suspend fun syncFilms(): EmptyResult<DataError.Network> {
        return remoteDataSource.getFilms()
            .onSuccess { films ->
                localDataSource.upsertFilms(films)
            }
            .asEmptyDataResult()
    }

    override suspend fun updateFavorite(id: String, isFavorite: Boolean) {
        localDataSource.updateFavorite(id, isFavorite)
    }
}
