package com.lihan.studioghibli.core.data.repository

import com.lihan.studioghibli.core.domain.datasource.FilmLocalDataSource
import com.lihan.studioghibli.core.domain.datasource.FilmRemoteDataSource
import com.lihan.studioghibli.core.domain.datastore.AppDataStore
import com.lihan.studioghibli.core.domain.model.Film
import com.lihan.studioghibli.core.domain.util.DataError
import com.lihan.studioghibli.core.domain.util.Result
import com.lihan.studioghibli.core.domain.util.TimeProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class OfflineFirstFilmRepositoryTest {

    private class FakeAppDataStore(initialDataExpired: Long = 0L) : AppDataStore {
        private val _dataExpired = MutableStateFlow(initialDataExpired)
        override val dataExpired: Flow<Long> = _dataExpired.asStateFlow()
        override suspend fun getDataExpired(): Long = _dataExpired.value
        override suspend fun setDataExpired(timestamp: Long) {
            _dataExpired.value = timestamp
        }
    }

    private class FakeTimeProvider(var currentTime: Long = 1_000_000L) : TimeProvider {
        override fun getCurrentTimeMillis(): Long = currentTime
    }

    private class FakeFilmRemoteDataSource : FilmRemoteDataSource {
        var shouldFail = false
        var fetchCount = 0
        val sampleFilms = listOf(
            Film(
                id = "1",
                title = "Totoro",
                originalTitle = "となりのトトロ",
                originalTitleRoman = "Tonari no Totoro",
                imageUrl = "totoro.jpg",
                movieBannerImageUrl = "banner.jpg",
                description = "desc",
                director = "Miyazaki",
                releaseDate = "1988",
                runningTime = "86",
                rtScore = "93"
            )
        )

        override suspend fun getFilms(): Result<List<Film>, DataError.Network> {
            fetchCount++
            return if (shouldFail) {
                Result.Error(DataError.Network.SERVER_ERROR)
            } else {
                Result.Success(sampleFilms)
            }
        }
    }

    private class FakeFilmLocalDataSource : FilmLocalDataSource {
        private val _films = MutableStateFlow<List<Film>>(emptyList())
        var upsertCount = 0

        override fun getFilms(): Flow<List<Film>> = _films.asStateFlow()

        override fun getFilmById(id: String): Flow<Film?> = _films.map { list -> list.find { it.id == id } }

        override suspend fun getFilmByIdDirect(id: String): Film? = _films.value.find { it.id == id }

        override suspend fun upsertFilms(films: List<Film>) {
            upsertCount++
            _films.value = films
        }

        override suspend fun updateFavorite(id: String, isFavorite: Boolean) {
            _films.value = _films.value.map {
                if (it.id == id) it.copy(isFavorite = isFavorite) else it
            }
        }
    }

    @Test
    fun `initial sync with dataExpired 0L fetches remote and updates dataExpired to 7 days later`() = runBlocking {
        val appDataStore = FakeAppDataStore(initialDataExpired = 0L)
        val timeProvider = FakeTimeProvider(currentTime = 1_000_000L)
        val remoteDataSource = FakeFilmRemoteDataSource()
        val localDataSource = FakeFilmLocalDataSource()

        val repository = OfflineFirstFilmRepository(
            remoteDataSource = remoteDataSource,
            localDataSource = localDataSource,
            appDataStore = appDataStore,
            timeProvider = timeProvider
        )

        val result = repository.syncFilms(forceRefresh = false)

        assertTrue(result is Result.Success)
        assertEquals(1, remoteDataSource.fetchCount)
        assertEquals(1, localDataSource.upsertCount)
        val expectedExpiredTime = 1_000_000L + OfflineFirstFilmRepository.SEVEN_DAYS_IN_MILLIS
        assertEquals(expectedExpiredTime, appDataStore.getDataExpired())
    }

    @Test
    fun `sync when not expired skips remote fetch`() = runBlocking {
        val initialExpired = 10_000_000L
        val appDataStore = FakeAppDataStore(initialDataExpired = initialExpired)
        val timeProvider = FakeTimeProvider(currentTime = 5_000_000L) // before expired
        val remoteDataSource = FakeFilmRemoteDataSource()
        val localDataSource = FakeFilmLocalDataSource()

        val repository = OfflineFirstFilmRepository(
            remoteDataSource = remoteDataSource,
            localDataSource = localDataSource,
            appDataStore = appDataStore,
            timeProvider = timeProvider
        )

        val result = repository.syncFilms(forceRefresh = false)

        assertTrue(result is Result.Success)
        assertEquals(0, remoteDataSource.fetchCount)
        assertEquals(0, localDataSource.upsertCount)
        assertEquals(initialExpired, appDataStore.getDataExpired())
    }

    @Test
    fun `sync when expired fetches remote and updates dataExpired`() = runBlocking {
        val initialExpired = 5_000_000L
        val appDataStore = FakeAppDataStore(initialDataExpired = initialExpired)
        val currentTime = 6_000_000L // after expired
        val timeProvider = FakeTimeProvider(currentTime = currentTime)
        val remoteDataSource = FakeFilmRemoteDataSource()
        val localDataSource = FakeFilmLocalDataSource()

        val repository = OfflineFirstFilmRepository(
            remoteDataSource = remoteDataSource,
            localDataSource = localDataSource,
            appDataStore = appDataStore,
            timeProvider = timeProvider
        )

        val result = repository.syncFilms(forceRefresh = false)

        assertTrue(result is Result.Success)
        assertEquals(1, remoteDataSource.fetchCount)
        assertEquals(1, localDataSource.upsertCount)
        val expectedExpiredTime = currentTime + OfflineFirstFilmRepository.SEVEN_DAYS_IN_MILLIS
        assertEquals(expectedExpiredTime, appDataStore.getDataExpired())
    }

    @Test
    fun `remote failure does not update dataExpired`() = runBlocking {
        val appDataStore = FakeAppDataStore(initialDataExpired = 0L)
        val timeProvider = FakeTimeProvider(currentTime = 1_000_000L)
        val remoteDataSource = FakeFilmRemoteDataSource().apply { shouldFail = true }
        val localDataSource = FakeFilmLocalDataSource()

        val repository = OfflineFirstFilmRepository(
            remoteDataSource = remoteDataSource,
            localDataSource = localDataSource,
            appDataStore = appDataStore,
            timeProvider = timeProvider
        )

        val result = repository.syncFilms(forceRefresh = false)

        assertTrue(result is Result.Error)
        assertEquals(1, remoteDataSource.fetchCount)
        assertEquals(0, localDataSource.upsertCount)
        assertEquals(0L, appDataStore.getDataExpired())
    }

    @Test
    fun `forceRefresh fetches remote even if not expired`() = runBlocking {
        val initialExpired = 10_000_000L
        val appDataStore = FakeAppDataStore(initialDataExpired = initialExpired)
        val currentTime = 5_000_000L
        val timeProvider = FakeTimeProvider(currentTime = currentTime)
        val remoteDataSource = FakeFilmRemoteDataSource()
        val localDataSource = FakeFilmLocalDataSource()

        val repository = OfflineFirstFilmRepository(
            remoteDataSource = remoteDataSource,
            localDataSource = localDataSource,
            appDataStore = appDataStore,
            timeProvider = timeProvider
        )

        val result = repository.syncFilms(forceRefresh = true)

        assertTrue(result is Result.Success)
        assertEquals(1, remoteDataSource.fetchCount)
        assertEquals(1, localDataSource.upsertCount)
        val expectedExpiredTime = currentTime + OfflineFirstFilmRepository.SEVEN_DAYS_IN_MILLIS
        assertEquals(expectedExpiredTime, appDataStore.getDataExpired())
    }
}
