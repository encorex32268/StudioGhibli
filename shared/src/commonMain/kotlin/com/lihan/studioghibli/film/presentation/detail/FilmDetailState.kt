package com.lihan.studioghibli.film.presentation.detail

import androidx.compose.runtime.Immutable
import com.lihan.studioghibli.core.presentation.util.UiText
import com.lihan.studioghibli.film.presentation.model.FilmUi

@Immutable
data class FilmDetailState(
    val film: FilmUi? = null,
    val isLoading: Boolean = false,
    val errorMessage: UiText? = null
)
