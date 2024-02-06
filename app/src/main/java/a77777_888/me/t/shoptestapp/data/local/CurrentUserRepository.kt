package a77777_888.me.t.shoptestapp.data.local

import a77777_888.me.t.shoptestapp.model.User
import android.content.Context
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

class CurrentUserRepository(context: Context, var user: User) {
    private val usersDAO by lazy(LazyThreadSafetyMode.NONE) {
        Room.databaseBuilder(
            context.applicationContext,
            UsersDatabase::class.java,
            "users.db"
        ).build().getUsersDAO()
    }

    var isPresentInDatabase = false

    init {
        runBlocking(Dispatchers.IO){
            val databaseUserEntity = usersDAO.getUser(user.hashCode().toLong())
            if (databaseUserEntity != null) {
                isPresentInDatabase = true
                user = User(databaseUserEntity)
            } else {
                isPresentInDatabase = false
                addUser()
            }
        }
    }

    private fun addUser() {
        usersDAO.addUser(UserEntity(user))
    }

    private fun updateUser() {
        usersDAO.updateUser(UserEntity(user))
    }

     fun deleteUser() {
        usersDAO.deleteUser(UserEntity(user))
    }

    fun addFavorite(favoriteId: String) {
        user.addFavorite(favoriteId)
        updateUser()
    }

    fun removeFavorite(favoriteId: String) {
        user.removeFavorite(favoriteId)
        updateUser()
    }
}