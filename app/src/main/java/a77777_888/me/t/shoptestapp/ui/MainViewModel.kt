package a77777_888.me.t.shoptestapp.ui

import a77777_888.me.t.shoptestapp.data.local.CurrentUserRepository
import a77777_888.me.t.shoptestapp.data.remote.MockSource
import a77777_888.me.t.shoptestapp.data.remote.entities.Item
import a77777_888.me.t.shoptestapp.model.User
import a77777_888.me.t.shoptestapp.ui.entities.SnackbarManager
import a77777_888.me.t.shoptestapp.ui.entities.SnackbarMessage.Companion.toSnackbarMessage
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private var _dataStateFlow = MutableStateFlow(DataState())
    val dataStateFlow
        get() = _dataStateFlow.asStateFlow()

    fun getRemoteData() = launchCatching {
        _dataStateFlow.update { DataState(loading = true) }
        try {
            val response = MockSource.mockAPI.getData()
            if (!response.isSuccessful || response.body() == null) {
                throw AppDataLoadException()
            } else
                _dataStateFlow.update { DataState(data =  response.body()!!.items) }
        } catch (e: Exception) {
            throw AppDataLoadException()
        }

    }

    private var  currentUserRepository: CurrentUserRepository? = null

    val currentUser
        get() = currentUserRepository?.user

    val isCurrentUserInDatabase
        get() = currentUserRepository?.isPresentInDatabase ?: false

    val currentUserFavoritesFlow
        get() = currentUserRepository?.user?.favoritesFlow ?: flowOf(emptyList())


    fun initCurrentUserRepository(context: Context, user: User) {
        currentUserRepository = CurrentUserRepository(context, user)
    }

    fun getItemById(id: String): Item? = dataStateFlow.value.data?.firstOrNull{ it.id == id }

    fun removeUser() {
        _dataStateFlow.value = DataState()
        launchCatching {
            currentUserRepository?.deleteUser()
        }
    }

    fun addFavoriteToDatabase(favoriteId: String) {
        launchCatching {
            currentUserRepository?.addFavorite(favoriteId)
        }
    }

    fun removeFavoriteFromDatabase(favoriteId: String) {
        launchCatching {
            currentUserRepository?.removeFavorite(favoriteId)
        }
    }

    private fun launchCatching(block: suspend CoroutineScope.() -> Unit) =
        viewModelScope.launch(Dispatchers.IO +
            CoroutineExceptionHandler { _, throwable ->

                if (throwable is AppDataLoadException){
                    _dataStateFlow.update { DataState(error = true) }
                }
                else  SnackbarManager.showMessage(throwable.toSnackbarMessage())
            },
            block = block
        )
}

data class DataState(
    val data: List<Item>? = null,
    val error: Boolean = false,
    val loading: Boolean = false
)

class AppDataLoadException : Exception()