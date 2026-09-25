package com.lihan.studioghibli.film.presentation.film

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun FilmRoot(
    onNavigateToDetail: (String) -> Unit = {},
    viewModel: FilmViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    FilmScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is FilmAction.OnFilmClick -> onNavigateToDetail(action.id)
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
fun FilmScreen(
    state: FilmState,
    onAction: (FilmAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize()
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            when {
                state.isLoading && state.films.isEmpty() -> {
                    CircularProgressIndicator()
                }
                state.errorMessage != null && state.films.isEmpty() -> {
                    Text(text = state.errorMessage.asString())
                }
                else -> {
                    LazyVerticalGrid(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        columns = GridCells.Fixed(1)
                    ){
                        items(
                            items = state.films,
                            key = { it.id }
                        ){ film ->
                            FilmCard(
                                film = film
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun FilmScreenPreview() {
    FilmScreen(
        state = FilmState(),
        onAction = {}
    )
}
