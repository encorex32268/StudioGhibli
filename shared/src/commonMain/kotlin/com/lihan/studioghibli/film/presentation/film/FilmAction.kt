package com.lihan.studioghibli.film.presentation.film

sealed interface FilmAction {
    data class OnFilmClick(val id: String) : FilmAction
    data object OnRefresh : FilmAction
}
