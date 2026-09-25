package com.lihan.studioghibli.core.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.lihan.studioghibli.core.data.local.entity.FilmEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FilmDao {
    @Query("SELECT * FROM films_table")
    fun getFilmsFlow(): Flow<List<FilmEntity>>

    @Query("SELECT * FROM films_table WHERE id = :id")
    fun getFilmByIdFlow(id: String): Flow<FilmEntity?>

    @Query("SELECT * FROM films_table WHERE id = :id")
    suspend fun getFilmById(id: String): FilmEntity?

    @Upsert
    suspend fun upsertFilms(films: List<FilmEntity>)

    @Query("UPDATE films_table SET isFavorite = :isFavorite WHERE id = :id")
    suspend fun updateFavorite(id: String, isFavorite: Boolean)

    @Transaction
    suspend fun upsertFilmsPreservingFavorites(newFilms: List<FilmEntity>) {
        for (film in newFilms) {
            val existing = getFilmById(film.id)
            if (existing != null) {
                // Preserve the existing isFavorite status
                upsertFilms(listOf(film.copy(isFavorite = existing.isFavorite)))
            } else {
                upsertFilms(listOf(film))
            }
        }
    }
}
