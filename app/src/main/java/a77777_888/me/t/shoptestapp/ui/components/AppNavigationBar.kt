package a77777_888.me.t.shoptestapp.ui.components

import a77777_888.me.t.shoptestapp.R
import a77777_888.me.t.shoptestapp.ui.entities.AppState
import a77777_888.me.t.shoptestapp.ui.entities.CATALOG_SCREEN
import a77777_888.me.t.shoptestapp.ui.entities.MAIN_SCREEN
import a77777_888.me.t.shoptestapp.ui.entities.PROFILE_SCREEN
import a77777_888.me.t.shoptestapp.ui.entities.ACTIONS_SCREEN
import a77777_888.me.t.shoptestapp.ui.entities.CART_SCREEN
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun AppNavigationBar(
    modifier:Modifier = Modifier,
    appState: AppState
) {

    BottomNavigation(
        modifier = modifier,
        backgroundColor = MaterialTheme.colorScheme.background,
        elevation = 16.dp,
    ) {

        var selectedIndex by remember { mutableIntStateOf(0) }

        listOf(
            NavigationItem(
                painterResource(id = R.drawable.ic_home),
                stringResource(id = R.string.main),
                { appState.clearAndNavigate(MAIN_SCREEN) }
            ),
            NavigationItem(
                painterResource(id = R.drawable.ic_catalog),
                stringResource(id = R.string.catalog),
                { appState.clearAndNavigate(CATALOG_SCREEN) }
            ),
            NavigationItem(
                painterResource(id = R.drawable.ic_cart),
                stringResource(id = R.string.cart),
                { appState.clearAndNavigate(CART_SCREEN) }
            ),
            NavigationItem(
                painterResource(id = R.drawable.ic_actions),
                stringResource(id = R.string.actions),
                { appState.clearAndNavigate(ACTIONS_SCREEN) }
            ),
            NavigationItem(
                painterResource(id = R.drawable.ic_profile),
                stringResource(id = R.string.profile),
                { appState.clearAndNavigate(PROFILE_SCREEN) }
            ),
        ).forEachIndexed{ index, item ->
            BottomNavigationItem(
                selected = index == selectedIndex,
                onClick = {
                    if (index != selectedIndex) {
                        item.onClick(); selectedIndex = index
                    }

                },
                icon = {
                    Icon(
                        painter = item.painter,
                        contentDescription = item.tile,
                        tint = if (index == selectedIndex) {
                            MaterialTheme.colorScheme.primary
                        } else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },
                label = {
                    Text(
                        text = item.tile,
                        style = MaterialTheme.typography.labelMedium,
                        color = if (index == selectedIndex) {
                            MaterialTheme.colorScheme.primary
                        } else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            )
        }

    }
}

private data class NavigationItem(
    val painter: Painter,
    val tile: String,
    val onClick: () -> Unit
)