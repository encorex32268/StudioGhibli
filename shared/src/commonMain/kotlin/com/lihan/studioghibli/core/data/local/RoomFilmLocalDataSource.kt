package com.lihan.studioghibli.core.data.local

import com.lihan.studioghibli.core.data.local.dao.FilmDao
import com.lihan.studioghibli.core.data.local.mapper.toDomain
import com.lihan.studioghibli.core.data.local.mapper.toEntity
import com.lihan.studioghibli.core.domain.datasource.FilmLocalDataSource
import com.lihan.studioghibli.core.domain.model.Film
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomFilmLocalDataSource(
    private val filmDao: FilmDao
) : FilmLocalDataSource {

    override fun getFilms(): Flow<List<Film>> {
        return filmDao.getFilmsFlow().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getFilmById(id: String): Flow<Film?> {
        return filmDao.getFilmByIdFlow(id).map { entity ->
            entity?.toDomain()
        }
    }

    override suspend fun getFilmByIdDirect(id: String): Film? {
        return filmDao.getFilmById(id)?.toDomain()
    }

    override suspend fun upsertFilms(films: List<Film>) {
        filmDao.upsertFilmsPreservingFavorites(films.map { it.toEntity() })
    }

    override suspend fun updateFavorite(id: String, isFavorite: Boolean) {
        filmDao.updateFavorite(id, isFavorite)
    }
}
