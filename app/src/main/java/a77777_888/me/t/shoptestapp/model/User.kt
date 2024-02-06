package a77777_888.me.t.shoptestapp.model

import a77777_888.me.t.shoptestapp.data.local.UserEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class User(
    val name: String,
    val surname: String,
    val phone: String,
) {
    private var _favorites = MutableStateFlow(listOf(""))
    val favoritesFlow
        get() = _favorites.asStateFlow()

    constructor(userEntity: UserEntity): this(
        name = userEntity.name,
        surname = userEntity.surname,
        phone = userEntity.phone
    ){ _favorites.value = userEntity.favorites.split(" ").filter{ it.isNotBlank() } }

    fun addFavorite(favoriteId: String) {
        _favorites.update {
            it + favoriteId
        }
    }

    fun removeFavorite(favoriteId: String) {
        _favorites.update {
            it - favoriteId
        }
    }
}
