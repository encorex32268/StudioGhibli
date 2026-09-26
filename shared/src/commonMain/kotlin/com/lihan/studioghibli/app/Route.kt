package com.lihan.studioghibli.app

import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    data object FilmRoute : Route

    @Serializable
    data class FilmDetailRoute(val filmId: String) : Route
}