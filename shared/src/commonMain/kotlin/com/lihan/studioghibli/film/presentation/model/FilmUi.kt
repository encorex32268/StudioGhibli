package com.lihan.studioghibli.film.presentation.model

/**
 * Presentation UI Model.
 * Formatted and optimized for Compose display.
 */
data class FilmUi(
    val id: String,
    val title: String,
    val originalTitle: String,
    val originalTitleRoman: String,
    val imageUrl: String,
    val movieBannerImageUrl: String,
    val description: String,
    val director: String,
    val releaseDate: String,
    val runningTime: String,
    val rtScore: String,
    val people: List<String> = emptyList(),
    val isFavorite: Boolean = false
)
