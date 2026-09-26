package com.lihan.studioghibli.film.di

import com.lihan.studioghibli.film.presentation.detail.FilmDetailViewModel
import com.lihan.studioghibli.film.presentation.film.FilmViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val filmModule = module {
    viewModelOf(::FilmViewModel)
    viewModel { params ->
        FilmDetailViewModel(
            filmId = params.get(),
            filmRepository = get()
        )
    }
}
