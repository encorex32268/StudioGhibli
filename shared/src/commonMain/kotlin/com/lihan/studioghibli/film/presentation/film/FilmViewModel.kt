package com.lihan.studioghibli.film.presentation.film

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lihan.studioghibli.core.domain.repository.FilmRepository
import com.lihan.studioghibli.core.domain.util.onFailure
import com.lihan.studioghibli.core.presentation.util.UiText
import com.lihan.studioghibli.core.presentation.util.asUiText
import com.lihan.studioghibli.film.presentation.mapper.toFilmUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FilmViewModel(
    private val filmRepository: FilmRepository
) : ViewModel() {

    private var hasLoadedInitialData = false
    private val _isLoading = MutableStateFlow(false)
    private val _errorMessage = MutableStateFlow<UiText?>(null)

    init {
        viewModelScope.launch {
            filmRepository.syncFilms()
        }
    }

    val state = combine(
        filmRepository.getFilms(),
        _isLoading,
        _errorMessage
    ) { films, isLoading, errorMessage ->
        FilmState(
            films = films.map { it.toFilmUi() },
            isLoading = isLoading,
            errorMessage = errorMessage
        )
    }.onStart {
        if (!hasLoadedInitialData) {
            hasLoadedInitialData = true
            refreshFilms()
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = FilmState(isLoading = true)
    )

    fun onAction(action: FilmAction) {
        when (action) {
            is FilmAction.OnToggleFavorite -> toggleFavorite(action.id, action.isFavorite)
            is FilmAction.OnRefresh -> refreshFilms()
            is FilmAction.OnFilmClick -> Unit
        }
    }

    private fun refreshFilms() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            filmRepository.syncFilms()
                .onFailure { error ->
                    _errorMessage.value = error.asUiText()
                }
            _isLoading.value = false
        }
    }

    private fun toggleFavorite(id: String, isFavorite: Boolean) {
        viewModelScope.launch {
            filmRepository.updateFavorite(id, isFavorite)
        }
    }
}
