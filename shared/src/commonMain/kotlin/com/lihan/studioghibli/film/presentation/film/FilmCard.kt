package com.lihan.studioghibli.film.presentation.film

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.lihan.studioghibli.core.presentation.ui.GhibliTheme
import com.lihan.studioghibli.core.presentation.ui.Gray
import com.lihan.studioghibli.core.presentation.ui.HeartFilled
import com.lihan.studioghibli.core.presentation.ui.MacaronPink
import com.lihan.studioghibli.core.presentation.ui.SoftDarkBg
import com.lihan.studioghibli.film.presentation.model.FilmUi
import com.lihan.studioghibli.film.presentation.preview.FilmPreview
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import studioghibli.shared.generated.resources.Res
import studioghibli.shared.generated.resources.minutes
import studioghibli.shared.generated.resources.star_filled

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun FilmCard(
    id: String,
    imageUrl: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    sharedTransitionScope: SharedTransitionScope? = null,
    animatedVisibilityScope: AnimatedVisibilityScope? = null,
    isFavorite: Boolean = false
) {
    Box(
        modifier = modifier
            .height(IntrinsicSize.Min)
            .clickable(
                indication = null,
                interactionSource = null,
                onClick = onClick
            ),
    ){
        AsyncImage(
            modifier = Modifier
                .padding(4.dp)
                .size(
                    width = 120.dp,
                    height = 180.dp
                )
                .then(
                    if (sharedTransitionScope != null && animatedVisibilityScope != null) {
                        with(sharedTransitionScope) {
                            Modifier.sharedElement(
                                sharedContentState = rememberSharedContentState(key = "film-image-${id}"),
                                animatedVisibilityScope = animatedVisibilityScope
                            )
                        }
                    } else Modifier
                )
                .clip(RoundedCornerShape(12.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant),
            model = imageUrl,
            contentDescription = imageUrl,
            contentScale = ContentScale.FillBounds,
        )
        if (isFavorite){
            IconButton(
                modifier = Modifier
                    .align(Alignment.TopEnd),
                onClick = {},
                enabled = false,
                shape = CircleShape
            ){
                Icon(
                    modifier = Modifier
                        .background(
                        color = SoftDarkBg.copy(alpha = 0.8f),
                        shape = CircleShape
                        )
                        .padding(8.dp),
                    imageVector = HeartFilled,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@Preview
@Composable
private fun FilmCardPreview() {
    GhibliTheme {
        FilmCard(
            id = FilmPreview.fileUi.id,
            imageUrl = FilmPreview.fileUi.imageUrl,
            onClick = {},
            isFavorite = true
        )
    }
}