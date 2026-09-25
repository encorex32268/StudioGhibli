package com.lihan.studioghibli.core.data.local.mapper

import com.lihan.studioghibli.core.data.local.entity.FilmEntity
import com.lihan.studioghibli.core.data.remote.FilmDto
import com.lihan.studioghibli.core.domain.model.Film

fun FilmEntity.toDomain(): Film {
    return Film(
        id = id,
        title = title,
        originalTitle = originalTitle,
        originalTitleRoman = originalTitleRoman,
        imageUrl = imageUrl,
        movieBannerImageUrl = movieBannerImageUrl,
        description = description,
        director = director,
        releaseDate = releaseDate,
        runningTime = runningTime,
        rtScore = rtScore,
        people = people,
        isFavorite = isFavorite
    )
}

fun Film.toEntity(): FilmEntity {
    return FilmEntity(
        id = id,
        title = title,
        originalTitle = originalTitle,
        originalTitleRoman = originalTitleRoman,
        imageUrl = imageUrl,
        movieBannerImageUrl = movieBannerImageUrl,
        description = description,
        director = director,
        releaseDate = releaseDate,
        runningTime = runningTime,
        rtScore = rtScore,
        people = people,
        isFavorite = isFavorite
    )
}

fun FilmDto.toDomain(isFavorite: Boolean = false): Film {
    return Film(
        id = id.orEmpty(),
        title = title.orEmpty(),
        originalTitle = original_title.orEmpty(),
        originalTitleRoman = original_title_romanised.orEmpty(),
        imageUrl = image.orEmpty(),
        movieBannerImageUrl = movie_banner.orEmpty(),
        description = description.orEmpty(),
        director = director.orEmpty(),
        releaseDate = release_date.orEmpty(),
        runningTime = running_time.orEmpty(),
        rtScore = rtScore.orEmpty(),
        people = people,
        isFavorite = isFavorite
    )
}
