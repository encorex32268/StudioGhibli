package com.lihan.studioghibli.film.presentation.film

sealed interface FilmAction {
    data class OnFilmClick(val id: String) : FilmAction
    data class OnToggleFavorite(val id: String, val isFavorite: Boolean) : FilmAction
    data object OnRefresh : FilmAction
}
