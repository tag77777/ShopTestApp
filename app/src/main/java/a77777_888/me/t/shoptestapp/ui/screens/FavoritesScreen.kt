package a77777_888.me.t.shoptestapp.ui.screens

import a77777_888.me.t.shoptestapp.R
import a77777_888.me.t.shoptestapp.data.remote.entities.Item
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import kotlinx.coroutines.flow.Flow

@Composable
fun FavoritesScreen(
    getItemById: (String) -> Item?,
    navigateTo: (String) -> Unit,
    removeFavorite: (String) -> Unit ,
    favoritesFlow: Flow<List<String>>,
) {
    val favorites = favoritesFlow.collectAsState(initial = listOf()).value

    Box(
        modifier = Modifier.fillMaxSize(),
    ){
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            state = rememberLazyGridState(),
        ) {
            if (favorites.isNotEmpty())
                items(
                    count = favorites.size,
                    key = { index -> favorites[index] },
                ) {
                    ItemScreen(
                        item = getItemById(favorites[it]),
                        addFavorite = {},
                        navigateTo = navigateTo,
                        removeFavorite = removeFavorite,
                        favoritesFlow = favoritesFlow
                    )
                }
        }

        if (favorites.isEmpty())
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = stringResource(R.string.empty_list),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center
            )
    }
}
