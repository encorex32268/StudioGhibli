package com.lihan.studioghibli.core.data.local

import com.lihan.studioghibli.core.data.local.dao.FilmDao
import com.lihan.studioghibli.core.data.local.entity.FilmEntity
import com.lihan.studioghibli.core.domain.model.Film
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class RoomFilmLocalDataSourceTest {

    private class FakeFilmDao : FilmDao {
        val films = mutableMapOf<String, FilmEntity>()
        private val filmsFlow = MutableStateFlow<List<FilmEntity>>(emptyList())

        private fun updateFlow() {
            filmsFlow.value = films.values.toList()
        }

        override fun getFilmsFlow(): Flow<List<FilmEntity>> = filmsFlow

        override fun getFilmByIdFlow(id: String): Flow<FilmEntity?> = filmsFlow.map { it.find { film -> film.id == id } }

        override suspend fun getFilmById(id: String): FilmEntity? = films[id]

        override suspend fun upsertFilms(films: List<FilmEntity>) {
            films.forEach { this.films[it.id] = it }
            updateFlow()
        }

        override suspend fun updateFavorite(id: String, isFavorite: Boolean) {
            films[id]?.let {
                films[id] = it.copy(isFavorite = isFavorite)
                updateFlow()
            }
        }
    }

    @Test
    fun `upsertFilms preserves existing isFavorite status`() = runBlocking {
        val dao = FakeFilmDao()
        val dataSource = RoomFilmLocalDataSource(dao)

        val film1 = Film(
            id = "totoro-123",
            title = "My Neighbor Totoro",
            originalTitle = "となりのトトロ",
            originalTitleRoman = "Tonari no Totoro",
            imageUrl = "image.jpg",
            movieBannerImageUrl = "banner.jpg",
            description = "desc",
            director = "Miyazaki",
            releaseDate = "1988",
            runningTime = "86",
            rtScore = "93",
            isFavorite = false
        )

        // 1. Initial insert (e.g. from remote)
        dataSource.upsertFilms(listOf(film1))
        assertEquals(false, dataSource.getFilmByIdDirect("totoro-123")?.isFavorite)

        // 2. User marks as favorite
        dataSource.updateFavorite("totoro-123", isFavorite = true)
        assertEquals(true, dataSource.getFilmByIdDirect("totoro-123")?.isFavorite)

        // 3. Remote data re-fetched (isFavorite from remote is false)
        val remoteFilmReFetched = film1.copy(title = "My Neighbor Totoro (Updated)")
        dataSource.upsertFilms(listOf(remoteFilmReFetched))

        // 4. Verify title updated but isFavorite is still true
        val updatedFilm = dataSource.getFilmByIdDirect("totoro-123")
        assertEquals("My Neighbor Totoro (Updated)", updatedFilm?.title)
        assertTrue(updatedFilm?.isFavorite == true, "isFavorite should be preserved as true!")
    }
}
