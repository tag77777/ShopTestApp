package a77777_888.me.t.shoptestapp.data.local

import a77777_888.me.t.shoptestapp.model.User
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity (
    @PrimaryKey val id: Long,
    val name: String,
    val surname: String,
    val phone: String,
    val favorites: String
){
    constructor(user: User): this(
        id = user.hashCode().toLong(),
        name = user.name,
        surname = user.surname,
        phone = user.phone,
        favorites = user.favoritesFlow.value.joinToString(separator = " ")
    )
}