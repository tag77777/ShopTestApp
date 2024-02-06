package a77777_888.me.t.shoptestapp.ui

import a77777_888.me.t.shoptestapp.R
import a77777_888.me.t.shoptestapp.ui.components.AppNavigationBar
import a77777_888.me.t.shoptestapp.ui.entities.ACTIONS_SCREEN
import a77777_888.me.t.shoptestapp.ui.entities.AppState
import a77777_888.me.t.shoptestapp.ui.entities.CART_SCREEN
import a77777_888.me.t.shoptestapp.ui.entities.CATALOG_SCREEN
import a77777_888.me.t.shoptestapp.ui.entities.FAVORITES_SCREEN
import a77777_888.me.t.shoptestapp.ui.entities.MAIN_SCREEN
import a77777_888.me.t.shoptestapp.ui.entities.PRODUCT_SCREEN
import a77777_888.me.t.shoptestapp.ui.entities.PROFILE_SCREEN
import a77777_888.me.t.shoptestapp.ui.entities.REGISTRATION_SCREEN
import a77777_888.me.t.shoptestapp.ui.entities.SnackbarManager
import a77777_888.me.t.shoptestapp.ui.screens.CatalogScreen
import a77777_888.me.t.shoptestapp.ui.screens.FavoritesScreen
import a77777_888.me.t.shoptestapp.ui.screens.MainScreen
import a77777_888.me.t.shoptestapp.ui.screens.ProductScreen
import a77777_888.me.t.shoptestapp.ui.screens.ProfileScreen
import a77777_888.me.t.shoptestapp.ui.screens.RegistrationScreen
import a77777_888.me.t.shoptestapp.ui.screens.UnderConstruction
import a77777_888.me.t.shoptestapp.ui.theme.ShopTestAppTheme
import android.annotation.SuppressLint
import android.content.res.Resources
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightNavigationBars = true
        val viewModel: MainViewModel by viewModels()
        setContent {

            ShopTestAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {

                        Main(
                            viewModel = viewModel,
                        )
                }
            }
        }
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun Main(
    viewModel: MainViewModel,
) {
    val appState: AppState = rememberAppState()
    var title by remember { mutableStateOf("") }
    var showNavigationBar by remember { mutableStateOf(false) }
    var showBackUpIcon by remember { mutableStateOf(false) }

    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = it,
                modifier = Modifier.padding(8.dp),
                snackbar = { snackbarData ->
                    Snackbar(snackbarData, contentColor = MaterialTheme.colorScheme.onPrimary)
                }
            )
        },
        scaffoldState = appState.scaffoldState,
        topBar = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (showBackUpIcon)
                        IconButton(onClick = { appState.popUp() } ) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                        }
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .weight(1f)
                            .offset(x = (-32).dp)
                    )
                }

        },
        bottomBar = { if (showNavigationBar)  AppNavigationBar(appState = appState) }
    ) { paddingValues ->
        NavHost(
            navController = appState.navController,
            startDestination = REGISTRATION_SCREEN,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            composable(REGISTRATION_SCREEN) {
                title = stringResource(id = R.string.registration_title)
                showNavigationBar = false
                showBackUpIcon = false

                RegistrationScreen(
                    navigateTo = appState::clearAndNavigate,
                    dataStateFlow = viewModel.dataStateFlow,
                    getRemoteData = viewModel::getRemoteData,
                    initCurrentUserRepository = viewModel::initCurrentUserRepository,
                    isCurrentUserInDatabase = viewModel.isCurrentUserInDatabase
                )
            }
            composable(MAIN_SCREEN) {
                title = stringResource(id = R.string.main)
                showNavigationBar = true
                showBackUpIcon = false

                MainScreen()
            }
            composable(CATALOG_SCREEN) {
                title = stringResource(id = R.string.catalog)
                showNavigationBar = true
                showBackUpIcon = false

                CatalogScreen(
                    itemList = viewModel.dataStateFlow.value.data!!,
                    navigateTo = appState::navigate,
                    addFavorite = viewModel::addFavoriteToDatabase,
                    removeFavorite = viewModel::removeFavoriteFromDatabase,
                    favoritesFlow = viewModel.currentUserFavoritesFlow
                )
            }
            composable(ACTIONS_SCREEN) {
                title = stringResource(id = R.string.actions)
                showNavigationBar = true
                showBackUpIcon = false

                UnderConstruction()
            }
            composable(CART_SCREEN) {
                title = stringResource(id = R.string.cart)
                showNavigationBar = true
                showBackUpIcon = false

                UnderConstruction()
            }
            composable(PROFILE_SCREEN) {
                title = stringResource(id = R.string.personal_account)
                showNavigationBar = true
                showBackUpIcon = false

                ProfileScreen(
                    user = viewModel.currentUser!!,
                    removeUser = viewModel::removeUser,
                    navigateTo = appState::navigate,
                    favoritesFlow = viewModel.currentUserFavoritesFlow
                )
            }
            composable("$PRODUCT_SCREEN/{userId}") {
                title = ""
                showNavigationBar = true
                showBackUpIcon = true

                val userId = it.arguments?.getString("userId") ?: ""
                val item = viewModel.getItemById(userId)
//                Log.e("TAG", "Main:id = $id item = $item")
                ProductScreen(
                    item = item,
                    addFavorite = viewModel::addFavoriteToDatabase,
                    removeFavorite = viewModel::removeFavoriteFromDatabase,
                    favoritesFlow = viewModel.currentUserFavoritesFlow
                )
            }
            composable(FAVORITES_SCREEN) {
                title = stringResource(id = R.string.favorites)
                showNavigationBar = true
                showBackUpIcon = true

                FavoritesScreen(
                    getItemById = viewModel::getItemById,
                    navigateTo = appState::navigate,
                    removeFavorite = viewModel::removeFavoriteFromDatabase,
                    favoritesFlow = viewModel.currentUserFavoritesFlow
                )
            }
        }
    }
}


@Composable
fun rememberAppState(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    navController: NavHostController = rememberNavController(),
    snackbarManager: SnackbarManager = SnackbarManager,
    resources: Resources = LocalContext.current.resources,
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) = remember(scaffoldState, navController, snackbarManager) {
        AppState(scaffoldState, navController, snackbarManager, resources, coroutineScope)
    }


//@Composable
//@ReadOnlyComposable
//fun resources(): Resources {
//  LocalConfiguration.current
//  return LocalContext.current.resources
//}

