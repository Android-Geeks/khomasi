package com.company.khomasi.presentation.components.cards

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.company.khomasi.R
import com.company.khomasi.domain.model.Playground
import com.company.khomasi.presentation.components.MyButton
import com.company.khomasi.presentation.components.iconButtons.FavoriteIcon
import com.company.khomasi.theme.darkCard
import com.company.khomasi.theme.darkText
import com.company.khomasi.theme.lightCard
import com.company.khomasi.theme.lightText
import com.company.khomasi.utils.convertToBitmap

@Composable
fun PlaygroundCard(
    playground: Playground,
    onFavouriteClick: () -> Unit,
    onViewPlaygroundClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PlaygroundCardViewModel = hiltViewModel(),
    context: Context = LocalContext.current,
    isDark: Boolean = isSystemInDarkTheme()
) {
    LaunchedEffect(Unit) {
        viewModel.updateUiState(playground, playground.isFavourite)
    }
    val playgroundCardState = viewModel.uiState.collectAsState()

    Card(
        colors = cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        shape = MaterialTheme.shapes.large,
        modifier = modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.large)
            .clickable {
                onViewPlaygroundClick()
            }
    ) {
        Column(
            Modifier.padding(8.dp)
        ) {
            Box {
                AsyncImage(
                    model = ImageRequest
                        .Builder(context = LocalContext.current)
                        .data(playground.playgroundPicture?.convertToBitmap())
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                    error = painterResource(id = R.drawable.playground),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(131.dp)
                        .clip(MaterialTheme.shapes.medium)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(62.dp)
                ) {
                    FavoriteIcon(
                        onFavoriteClick = {
                            val isFavorite = !playgroundCardState.value.isFavorite
                            viewModel.updateFavorite(isFavorite)
                            viewModel.updateUserFavourite(
                                playground.id.toString(),
                                playground.isFavourite
                            )
//
//                           if (isFavorite) {
//                               viewModel.userFavourite(
//                                   playgroundId = playgroundCardState.value.playground.id.toString()
//                               )
//                           } else {
//                               viewModel.deleteUserFavourite(
//                                   playgroundId = playgroundCardState.value.playground.id.toString()
//                               )
//                           }
                        },
                        isFavorite = playgroundCardState.value.isFavorite,
                        modifier = Modifier.padding(top = 12.dp, start = 6.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = stringResource(
                            if (playground.isBookable)
                                R.string.bookable
                            else
                                R.string.un_bookable
                        ),
                        textAlign = TextAlign.Start,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier
                            .padding(top = 12.dp)
                            .clip(RoundedCornerShape(topStart = 4.dp, bottomStart = 4.dp))
                            .background(
                                color = if (isDark) darkCard else lightCard
                            )
                            .padding(start = 5.dp, end = 5.dp, top = 5.dp, bottom = 5.dp)
                    )

                }
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = playground.name,
                    textAlign = TextAlign.Start,
                    color = if (isDark) darkText else lightText,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )

                Text(
                    text = String.format("%.1f", playground.rating),
                    textAlign = TextAlign.End,
                    color = if (isDark) darkText else lightText,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                        .padding(top = 8.dp)
                )
                Icon(
                    painter = painterResource(id = R.drawable.unfilled_star),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(top = 13.dp, start = 4.dp)
                )

            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.mappin),
                    contentDescription = null,
                )
                Text(
                    text = context.getString(
                        R.string.distance_away,
                        String.format("%.1f", playground.distance)
                    ),
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.tertiary,
                )
            }
            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                thickness = 1.dp
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.currencycircledollar),
                    contentDescription = null,
                )
                Text(
                    text = context.getString(R.string.fees_per_hour, playground.feesForHour),
                    textAlign = TextAlign.End,
                    color = if (isDark) darkText else lightText,
                    style = MaterialTheme.typography.bodyLarge,
                )
                Spacer(modifier = Modifier.weight(1f))
                MyButton(text =  R.string.view_playground,
                    onClick = {   onViewPlaygroundClick()},
                    modifier = Modifier
                        .weight(3f)
                        .height(48.dp)
                )
            }
        }
    }
}
