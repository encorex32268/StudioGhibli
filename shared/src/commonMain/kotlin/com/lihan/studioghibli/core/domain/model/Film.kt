package com.lihan.studioghibli.core.domain.model

data class Film(
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
