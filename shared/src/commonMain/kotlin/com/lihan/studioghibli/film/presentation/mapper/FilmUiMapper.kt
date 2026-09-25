package com.lihan.studioghibli.film.presentation.mapper

import com.lihan.studioghibli.core.domain.model.Film
import com.lihan.studioghibli.film.presentation.model.FilmUi

fun Film.toFilmUi(): FilmUi {
    return FilmUi(
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
