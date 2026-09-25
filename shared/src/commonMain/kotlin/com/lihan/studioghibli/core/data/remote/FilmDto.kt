package com.lihan.studioghibli.core.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class FilmDto(
    val id: String?,
    val title: String?,
    val original_title: String?,
    val original_title_romanised: String?,
    val image: String?,
    val movie_banner: String?,
    val description: String?,
    val director: String?,
    val release_date: String?,
    val running_time: String?,
    val rt_score: String?,
    val people: List<String> = emptyList()
)
