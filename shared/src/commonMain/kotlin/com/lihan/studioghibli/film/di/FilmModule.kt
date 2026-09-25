package com.lihan.studioghibli.film.di

import com.lihan.studioghibli.film.presentation.film.FilmViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val filmModule = module {
    viewModelOf(::FilmViewModel)
}
