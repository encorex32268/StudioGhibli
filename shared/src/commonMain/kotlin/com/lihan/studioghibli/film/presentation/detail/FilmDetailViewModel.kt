package com.lihan.studioghibli.film.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lihan.studioghibli.core.domain.repository.FilmRepository
import com.lihan.studioghibli.film.presentation.mapper.toFilmUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FilmDetailViewModel(
    private val filmId: String,
    private val filmRepository: FilmRepository
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)

    val state = combine(
        filmRepository.getFilmById(filmId),
        _isLoading
    ) { film, isLoading ->
        FilmDetailState(
            film = film?.toFilmUi(),
            isLoading = isLoading
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = FilmDetailState(isLoading = true)
    )

    fun onAction(action: FilmDetailAction) {
        when (action) {
            is FilmDetailAction.OnToggleFavorite -> toggleFavorite()
            is FilmDetailAction.OnBackClick -> Unit
        }
    }

    private fun toggleFavorite() {
        val currentFilm = state.value.film ?: return
        viewModelScope.launch {
            filmRepository.updateFavorite(currentFilm.id, !currentFilm.isFavorite)
        }
    }
}
