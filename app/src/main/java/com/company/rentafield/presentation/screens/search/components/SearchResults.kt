package com.company.rentafield.presentation.screens.search.components

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.company.rentafield.R
import com.company.rentafield.domain.model.playground.Playground
import com.company.rentafield.presentation.theme.RentafieldTheme
import java.util.Locale


@Composable
fun SearchResults(
    playgrounds: List<Playground>,
    query: String,
    onQueryChange: (String) -> Unit,
    onSearchQuerySubmitted: (String) -> Unit,
    navigateToPlaygroundDetails: (Int, Boolean) -> Unit,
    onNextPage: () -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(playgrounds.take(10)) { item ->
            SearchResultsItem(
                text = item.name,
                distance = String.format(locale = Locale.getDefault(), "%.1f", item.distance),
                onClick = {
                    onQueryChange(query)
                    onSearchQuerySubmitted(query)
                    navigateToPlaygroundDetails(item.id, item.isFavourite)
                },
            )
        }

        item {
            TextButton(
                onClick = {
                    onSearchQuerySubmitted(query)
                    onNextPage()
                },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.show_all_results),
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}


@Composable
fun SearchResultsItem(
    text: String,
    onClick: () -> Unit,
    distance: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clickable {
                onClick()
            }
    ) {
        Icon(
            painter = painterResource(id = R.drawable.arrowupleft),
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = text,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.SemiBold
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = LocalContext.current.getString(R.string.distance_away, distance),
                color = MaterialTheme.colorScheme.tertiary,
                style = MaterialTheme.typography.labelSmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

    }
}

@Preview(
    name = "DARK | EN",
    locale = "en",
    uiMode = UI_MODE_NIGHT_YES,
    backgroundColor = 0xFF0E0E0E,
    showBackground = true
)
@Preview(
    name = "LIGHT | AR",
    locale = "ar",
    uiMode = UI_MODE_NIGHT_NO,
    backgroundColor = 0xFFF5F5F5,
    showBackground = true
)
@Composable
fun SearchResultItemPreview() {
    RentafieldTheme {
        SearchResultsItem(
            text = "test",
            onClick = {},
            distance = "10"
        )
    }
}