package com.lihan.studioghibli.app

import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    data object FilmRoute

}