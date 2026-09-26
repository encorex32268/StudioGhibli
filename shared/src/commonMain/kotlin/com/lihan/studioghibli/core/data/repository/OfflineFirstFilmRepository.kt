package com.lihan.studioghibli.core.data.repository

import com.lihan.studioghibli.core.domain.datasource.FilmLocalDataSource
import com.lihan.studioghibli.core.domain.datasource.FilmRemoteDataSource
import com.lihan.studioghibli.core.domain.datastore.AppDataStore
import com.lihan.studioghibli.core.domain.model.Film
import com.lihan.studioghibli.core.domain.repository.FilmRepository
import com.lihan.studioghibli.core.domain.util.DataError
import com.lihan.studioghibli.core.domain.util.EmptyResult
import com.lihan.studioghibli.core.domain.util.Result
import com.lihan.studioghibli.core.domain.util.TimeProvider
import com.lihan.studioghibli.core.domain.util.asEmptyDataResult
import com.lihan.studioghibli.core.domain.util.onSuccess
import kotlinx.coroutines.flow.Flow

class OfflineFirstFilmRepository(
    private val remoteDataSource: FilmRemoteDataSource,
    private val localDataSource: FilmLocalDataSource,
    private val appDataStore: AppDataStore
) : FilmRepository {

    override fun getFilms(): Flow<List<Film>> {
        return localDataSource.getFilms()
    }

    override fun getFilmById(id: String): Flow<Film?> {
        return localDataSource.getFilmById(id)
    }

    override suspend fun syncFilms(forceRefresh: Boolean): EmptyResult<DataError.Network> {
        val currentTime = TimeProvider.getCurrentTimeMillis()
        if (!forceRefresh) {
            val dataExpired = appDataStore.getDataExpired()
            if (currentTime < dataExpired) {
                return Result.Success(Unit)
            }
        }

        return remoteDataSource.getFilms()
            .onSuccess { films ->
                localDataSource.upsertFilms(films)
                val newExpiredTime = currentTime + SEVEN_DAYS_IN_MILLIS
                appDataStore.setDataExpired(newExpiredTime)
            }
            .asEmptyDataResult()
    }

    override suspend fun updateFavorite(id: String, isFavorite: Boolean) {
        localDataSource.updateFavorite(id, isFavorite)
    }

    companion object {
        const val SEVEN_DAYS_IN_MILLIS = 7L * 24 * 60 * 60 * 1000
    }
}

