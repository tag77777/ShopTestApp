package a77777_888.me.t.shoptestapp.ui.screens

import a77777_888.me.t.shoptestapp.R
import a77777_888.me.t.shoptestapp.data.remote.entities.Feedback
import a77777_888.me.t.shoptestapp.data.remote.entities.Info
import a77777_888.me.t.shoptestapp.data.remote.entities.Item
import a77777_888.me.t.shoptestapp.data.remote.entities.Price
import a77777_888.me.t.shoptestapp.ui.components.MyPager
import a77777_888.me.t.shoptestapp.ui.entities.PRODUCT_SCREEN
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogScreen(
    itemList: List<Item>,
    navigateTo: (String) -> Unit,
    addFavorite: (String) -> Unit,
    removeFavorite: (String) -> Unit,
    favoritesFlow: Flow<List<String>>,
) {
    var items by remember {
        mutableStateOf(itemList)
    }

    var sortingTitle by remember { mutableIntStateOf(R.string.by_popularity) }
    var sortingExpanded by remember { mutableStateOf(false) }
    val sortingMap = mapOf(
        R.string.by_popularity to { list: List<Item> -> list.sortedBy { it.feedback.rating }.reversed() },
        R.string.price_up to { list: List<Item> -> list.sortedBy { it.price.priceWithDiscount.toIntOrNull() ?: Int.MAX_VALUE } },
        R.string.price_down to { list: List<Item> -> list.sortedBy { it.price.priceWithDiscount.toIntOrNull() ?: Int.MIN_VALUE }.reversed() }
    )

    var filterSelected by remember { mutableIntStateOf(R.string.watchAll) }
    val filterMap = mapOf(
        R.string.watchAll to { list: List<Item> -> list},
        R.string.face to { list: List<Item> -> list.filter { it.tags.contains("face") } },
        R.string.body to { list: List<Item> -> list.filter { it.tags.contains("body") } },
        R.string.suntan to { list: List<Item> -> list.filter { it.tags.contains("suntan") } },
        R.string.mask to { list: List<Item> -> list.filter { it.tags.contains("mask") } },
    )

    val transformation = { items = sortingMap[sortingTitle]!!(filterMap[filterSelected]!!(itemList)) }

    LaunchedEffect(key1 = null) {
        transformation()
    }

    Column(
        modifier = Modifier.fillMaxSize(),
//        verticalArrangement = Arrangement.Center,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Column {
                TextButton(onClick = { sortingExpanded = true }) {
                    Row {
                        Icon(
                            painterResource(id = R.drawable.ic_sorting),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                        Text(text = stringResource(id = sortingTitle))
                        Icon(
                            painterResource(id = R.drawable.ic_dropdown),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }

                DropdownMenu(expanded = sortingExpanded, onDismissRequest = { sortingExpanded = false }) {
                    sortingMap.filter { it.key != sortingTitle }.forEach{
                        DropdownMenuItem(
                            onClick = { sortingTitle = it.key; transformation(); sortingExpanded = false }
                        ) {
                            Text(text = stringResource(id = it.key))
                        }
                    }
                }
            }

            Column {
                TextButton(onClick = { }) {
                    Row {
                        Icon(painterResource(
                            id = R.drawable.ic_filter),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                        Text(text = stringResource(id = R.string.filters))
                    }
                }
            }

        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            filterMap.forEach{
                FilterChip(
                    modifier = Modifier.padding(horizontal = 4.dp),
                    selected = it.key == filterSelected,
                    onClick = { filterSelected = it.key; transformation() },
                    label = { Text(
                        text = stringResource(id = it.key),
                        color = if (filterSelected == it.key) MaterialTheme.colorScheme.inverseOnSurface
                                else MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                            },
                    trailingIcon = {
                        if (it.key == filterSelected)
                            Icon(
                                Icons.Default.Close,
                                contentDescription = stringResource(id = it.key),
                                modifier = Modifier.requiredSize(FilterChipDefaults.IconSize)
                            )
                    },
                    border = null,
                    shape = RoundedCornerShape(16.dp),
                    colors = FilterChipDefaults.filterChipColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                        selectedContainerColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                )
            }
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            state = rememberLazyGridState(),
            ) {
            items(
                count = items.size,
                key = {index -> items[index].id }
            ){
                ItemScreen(
                    item = items[it],
                    navigateTo = navigateTo,
                    addFavorite = addFavorite,
                    removeFavorite = removeFavorite,
                    favoritesFlow = favoritesFlow)
            }
        }
    }
}

@Composable
fun ItemScreen(
    item: Item?,
    navigateTo: (String) -> Unit,
    addFavorite: (String) -> Unit,
    removeFavorite: (String) -> Unit,
    favoritesFlow: Flow<List<String>>,
) {
    item?.let {
        val favorites = favoritesFlow.collectAsState(initial = listOf()).value

        Column(
            modifier = Modifier
                .padding(8.dp)
                .clickable {
                    val itemId = item.id
                    navigateTo("$PRODUCT_SCREEN/$itemId")
                }
        ) {

            Box(
                modifier = Modifier
                    .height(144.dp)
                    .fillMaxWidth()
            ) {
                MyPager(
                    modifier = Modifier.fillMaxSize(0.9f),        //.height(IntrinsicSize.Max),
                    indicatorColor = MaterialTheme.colorScheme.primary,
                    indicatorHeight = 4.dp,
                    imageContentScale = ContentScale.Fit
                )

                IconButton(
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.TopEnd)
                        .offset((-24).dp),
                    onClick = {
                        if (favorites.contains(item.id)) removeFavorite(item.id) else addFavorite(item.id)
                    }
                ) {
                    Icon(
                        imageVector = if (favorites.contains(item.id)) Icons.Default.Favorite
                        else Icons.Default.FavoriteBorder,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                    )
                }
            }

            Text(
                text = item.price.price + item.price.unit,
                style = MaterialTheme.typography.labelSmall,
                textDecoration = TextDecoration.LineThrough,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = item.price.priceWithDiscount + item.price.unit,
                    style = MaterialTheme.typography.displayLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = stringResource(R.string.price_discount, item.price.discount.toString()),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier
                        .padding(start = 4.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primary,
                            shape = MaterialTheme.shapes.small
                        )
                )
            }

            Text(
                text = item.title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = item.subtitle,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onBackground
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.tertiary
                )
                Text(
                    text = item.feedback.rating.toString(),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.tertiary
                )
                Text(
                    text = "(${item.feedback.count})",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

//        Button(
//            modifier = Modifier.align(Alignment.End), //.size(28.dp),
//            onClick = {  },
//            shape = RoundedCornerShape(topStart = 8.dp, topEnd = 0.dp, bottomEnd = 8.dp, bottomStart = 0.dp)
//        ) {
//            Text(
//                text = "+",
//                style = MaterialTheme.typography.titleLarge,
//                color = MaterialTheme.colorScheme.onPrimary
//            )
//        }

            Text(
                modifier = Modifier
                    .align(Alignment.End)
                    .size(28.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(
                            topStart = 8.dp,
                            topEnd = 0.dp,
                            bottomEnd = 8.dp,
                            bottomStart = 0.dp
                        )
                    ),
                text = "+",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimary,
                textAlign = TextAlign.Center
            )

        }
    }


}

@Preview(showBackground = true)
@Composable
fun PreviewItemScreen() {
    ItemScreen(item = testItem, {},{}, {}, favoritesFlow = testFavoritesFlow)
}


val testItem = Item(
    id = "1",
    title = "ESFOLIO",
    subtitle = "Лосьон для тела `ESFOLIO` COENZYME Q10 Увлажняющий 500 мл",
    price = Price(
        price = "1000",
        discount = 99,
        priceWithDiscount = "2000",
        unit = "₽"
    ),
    feedback = Feedback(
        count = 51,
        rating = 4.5f
    ),
    tags = listOf( "body", "suntan", "mask"),
    available = 100,
    description = "Лосьон для тела `ESFOLIO` COENZYME Q10 Увлажняющий содержит минеральную воду и соду, способствует глубокому очищению пор от различных загрязнений, контроллирует работу сальных желез, сужает поры. Обладает мягким антиептическим действием, не пересушивает кожу. Минеральная вода тонизирует и смягчает кожу.",
    info = listOf(Info(	"Артикул товара","441187")),
    ingredients = "Glycerin Palmitic Acid, Stearic Acid, Capric Acid, Sodium Benzoate"
)

val testFavoritesFlow = flowOf(listOf("cbf0c984-7c6c-4ada-82da-e29dc698bb50"))
