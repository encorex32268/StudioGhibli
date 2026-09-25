package com.lihan.studioghibli.film.presentation.film

import com.lihan.studioghibli.core.presentation.util.UiText
import com.lihan.studioghibli.film.presentation.model.FilmUi

data class FilmState(
    val films: List<FilmUi> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: UiText? = null
)
