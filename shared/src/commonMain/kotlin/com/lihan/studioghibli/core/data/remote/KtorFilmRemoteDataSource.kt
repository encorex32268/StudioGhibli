package com.lihan.studioghibli.core.data.remote

import com.lihan.studioghibli.core.data.networking.get
import com.lihan.studioghibli.core.data.local.mapper.toDomain
import com.lihan.studioghibli.core.domain.datasource.FilmRemoteDataSource
import com.lihan.studioghibli.core.domain.model.Film
import com.lihan.studioghibli.core.domain.util.DataError
import com.lihan.studioghibli.core.domain.util.Result
import com.lihan.studioghibli.core.domain.util.map
import io.ktor.client.HttpClient

class KtorFilmRemoteDataSource(
    private val httpClient: HttpClient
) : FilmRemoteDataSource {

    override suspend fun getFilms(): Result<List<Film>, DataError.Network> {
        return httpClient.get<List<FilmDto>>("/films")
            .map { dtos -> dtos.map { it.toDomain() } }
    }
}
