package com.lihan.studioghibli.core.domain.datasource

import com.lihan.studioghibli.core.domain.model.Film
import com.lihan.studioghibli.core.domain.util.DataError
import com.lihan.studioghibli.core.domain.util.Result

interface FilmRemoteDataSource {
    suspend fun getFilms(): Result<List<Film>, DataError.Network>
}
