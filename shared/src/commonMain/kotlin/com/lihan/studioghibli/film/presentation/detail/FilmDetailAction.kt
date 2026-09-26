package com.lihan.studioghibli.film.presentation.detail

sealed interface FilmDetailAction {
    data object OnBackClick : FilmDetailAction
    data object OnToggleFavorite : FilmDetailAction
}
