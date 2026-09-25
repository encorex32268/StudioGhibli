package com.lihan.studioghibli.core.domain.datasource

import com.lihan.studioghibli.core.domain.model.Film
import kotlinx.coroutines.flow.Flow

interface FilmLocalDataSource {
    fun getFilms(): Flow<List<Film>>
    fun getFilmById(id: String): Flow<Film?>
    suspend fun getFilmByIdDirect(id: String): Film?
    suspend fun upsertFilms(films: List<Film>)
    suspend fun updateFavorite(id: String, isFavorite: Boolean)
}
