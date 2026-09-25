package com.lihan.studioghibli.core.domain.repository

import com.lihan.studioghibli.core.domain.model.Film
import com.lihan.studioghibli.core.domain.util.DataError
import com.lihan.studioghibli.core.domain.util.EmptyResult
import kotlinx.coroutines.flow.Flow

interface FilmRepository {
    fun getFilms(): Flow<List<Film>>
    fun getFilmById(id: String): Flow<Film?>
    suspend fun syncFilms(): EmptyResult<DataError.Network>
    suspend fun updateFavorite(id: String, isFavorite: Boolean)
}
