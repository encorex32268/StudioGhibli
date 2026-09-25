package com.lihan.studioghibli.film.presentation.film

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.lihan.studioghibli.core.presentation.ui.GhibliTheme
import com.lihan.studioghibli.core.presentation.ui.Gray
import com.lihan.studioghibli.film.presentation.model.FilmUi
import org.jetbrains.compose.resources.stringResource
import studioghibli.shared.generated.resources.Res
import studioghibli.shared.generated.resources.minutes

@Composable
fun FilmCard(
    film: FilmUi,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        onClick = {},
        shape = RoundedCornerShape(12.dp),
        shadowElevation = 4.dp
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
        ){
            AsyncImage(
                modifier = Modifier
                    .padding(4.dp)
                    .size(
                        width = 120.dp,
                        height = 180.dp
                    )
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                model = film.imageUrl,
                contentDescription = film.title,
                contentScale = ContentScale.FillBounds,
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ){
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        modifier = Modifier.weight(1f),
                        text = film.originalTitle,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = film.releaseDate,
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = Gray
                        )
                    )
                }
                Text(
                    text = film.title,
                    style = MaterialTheme.typography.titleSmall
                )
                Spacer(Modifier.weight(1f))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        text = "${film.director}・${film.runningTime}"+ stringResource(Res.string.minutes),
                        style = MaterialTheme.typography.titleSmall
                    )
                }
            }
        }

    }
}

@Preview
@Composable
private fun FilmCardPreview() {
    GhibliTheme {
        FilmCard(
            film = FilmUi(
                id = "",
                title = "My Neighbor Totoro",
                originalTitle = "となりのトトロ",
                originalTitleRoman = "Tonari no Totoro",
                description = "Two sisters move to the country with their father in order to be closer to their hospitalized mother, and discover the surrounding trees are inhabited by Totoros, magical spirits of the forest. When the youngest runs away from home," +
                        " the older sister seeks help from the spirits to find her.",
                director = "Hayao Miyazaki",
                releaseDate = "1988",
                runningTime = "86",
                rtScore = "93",
                imageUrl = "",
                movieBannerImageUrl = "",
                people = emptyList()
            )
        )
    }
}