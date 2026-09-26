package com.lihan.studioghibli.film.presentation.detail

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.lihan.studioghibli.core.presentation.ui.ArrowLeft
import com.lihan.studioghibli.core.presentation.ui.Gray
import com.lihan.studioghibli.core.presentation.ui.HeartFilled
import com.lihan.studioghibli.core.presentation.ui.HeartOutline
import com.lihan.studioghibli.core.presentation.ui.MacaronPink
import com.lihan.studioghibli.film.presentation.model.FilmUi
import com.lihan.studioghibli.film.presentation.preview.FilmPreview
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import studioghibli.shared.generated.resources.Res
import studioghibli.shared.generated.resources.back
import studioghibli.shared.generated.resources.director
import studioghibli.shared.generated.resources.favorite
import studioghibli.shared.generated.resources.heart_fill
import studioghibli.shared.generated.resources.minutes
import studioghibli.shared.generated.resources.release_date
import studioghibli.shared.generated.resources.star_filled
import studioghibli.shared.generated.resources.synopsis

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun FilmDetailRoot(
    filmId: String,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    sharedTransitionScope: SharedTransitionScope? = null,
    animatedVisibilityScope: AnimatedVisibilityScope? = null,
    viewModel: FilmDetailViewModel = koinViewModel(key = filmId) { parametersOf(filmId) }
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    FilmDetailScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is FilmDetailAction.OnBackClick -> onNavigateBack()
                else -> Unit
            }
            viewModel.onAction(action)
        },
        modifier = modifier,
        sharedTransitionScope = sharedTransitionScope,
        animatedVisibilityScope = animatedVisibilityScope
    )
}

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalLayoutApi::class)
@Composable
fun FilmDetailScreen(
    state: FilmDetailState,
    onAction: (FilmDetailAction) -> Unit,
    modifier: Modifier = Modifier,
    sharedTransitionScope: SharedTransitionScope? = null,
    animatedVisibilityScope: AnimatedVisibilityScope? = null
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        val film = state.film
        if (state.isLoading && film == null) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else if (film != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                // Hero Banner & Poster Box
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(360.dp)
                ) {
                    if (film.movieBannerImageUrl.isNotBlank()) {
                        AsyncImage(
                            model = film.movieBannerImageUrl,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .graphicsLayer{
                                    alpha = 0.7f
                                }
                                .fillMaxSize()
                                .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)),
                        )
                        // Gradient Overlay on Banner
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.verticalGradient(
                                        colors = listOf(
                                            Color.Black.copy(alpha = 0.35f),
                                            Color.Transparent,
                                            MaterialTheme.colorScheme.background
                                        )
                                    )
                                )
                        )
                    }

                    // Shared Element Poster
                    Surface(
                        modifier = Modifier.align(Alignment.BottomCenter),
                        shape = RoundedCornerShape(12.dp),
                        shadowElevation = 4.dp
                    ) {
                        AsyncImage(
                            model = film.imageUrl,
                            contentDescription = film.title,
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier
                                .size(width = 180.dp, height = 240.dp)
                                .then(
                                    if (sharedTransitionScope != null && animatedVisibilityScope != null) {
                                        with(sharedTransitionScope) {
                                            Modifier.sharedElement(
                                                sharedContentState = rememberSharedContentState(key = "film-image-${film.id}"),
                                                animatedVisibilityScope = animatedVisibilityScope
                                            )
                                        }
                                    } else Modifier
                                )
                                .clip(RoundedCornerShape(12.dp))
                                .background(MaterialTheme.colorScheme.surfaceVariant)
                        )
                    }
                }

                // Info Section
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 12.dp)
                ) {
                    Text(
                        text = film.title,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.height(4.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Text(
                            text = film.originalTitle,
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onBackground,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.weight(1f),
                        )
                        Text(
                            text = film.releaseDate,
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = Gray
                            )
                        )
                    }
                    Text(
                        text = film.originalTitleRoman,
                        style = MaterialTheme.typography.bodySmall,
                        color = Gray
                    )
                    Spacer(modifier = Modifier.height(8.dp))


                    // Director Info
                    Row(
                        modifier = Modifier.fillMaxWidth().basicMarquee(),
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Text(
                            text = "${film.director}・${film.runningTime}"+ stringResource(Res.string.minutes) + "・",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Image(
                            painter = painterResource(Res.drawable.star_filled),
                            contentDescription = film.id
                        )
                        Text(
                            text = film.rtScore,
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                    HorizontalDivider(
                        modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth(fraction = 0.2f)
                        .padding(vertical = 24.dp),
                        color = MaterialTheme.colorScheme.outlineVariant
                    )

                    // Synopsis Header
                    Text(
                        text = stringResource(Res.string.synopsis),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = film.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.85f),
                        lineHeight = MaterialTheme.typography.bodyLarge.lineHeight
                    )

                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        }

        // Floating Top Bar with Back and Favorite
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { onAction(FilmDetailAction.OnBackClick) },
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f)
                ),
                shape = CircleShape
            ) {
                Icon(
                    imageVector = ArrowLeft,
                    contentDescription = stringResource(Res.string.back),
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }

            IconButton(
                onClick = { onAction(FilmDetailAction.OnToggleFavorite) },
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.3f)
                ),
                shape = CircleShape
            ) {
                val isFavorite = film?.isFavorite == true
                Icon(
                    imageVector = if (isFavorite) HeartFilled else HeartOutline,
                    contentDescription = stringResource(Res.string.favorite),
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}



@Preview
@Composable
private fun FilmDetailScreenPreview() {
    com.lihan.studioghibli.core.presentation.ui.GhibliTheme {
        FilmDetailScreen(
            state = FilmDetailState(
                film = FilmPreview.fileUi
            ),
            onAction = {}
        )
    }
}
