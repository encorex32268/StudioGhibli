package com.lihan.studioghibli.core.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "films_table")
data class FilmEntity(
    @PrimaryKey
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
