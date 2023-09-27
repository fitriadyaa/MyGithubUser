package com.fitriadyaa.submission_githubuser.repository

import androidx.lifecycle.LiveData
import com.fitriadyaa.submission_githubuser.data.local.entity.UserEntity
import com.fitriadyaa.submission_githubuser.data.local.room.UserDao

class UserRepository(
    private val userDao: UserDao,
) {

    fun getFavoriteUser(): LiveData<List<UserEntity>>{
        return userDao.getAllFavorite()
    }

    fun setFavorite(username: String, avatar : String, id: Int, favoritesState: Boolean){
        val user = UserEntity(id, username, avatar, favoritesState)
//        favorites.isFavorite = favoritesState
        userDao.insert(user)
    }

    fun deleteFavorite(id : Int){
        userDao.delete(id)
    }

    suspend fun check(id: Int): Int {
        return userDao.check(id)
    }
    companion object{
        var username = "KEY_DATA"

        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            userDao: UserDao
        ) : UserRepository = instance ?: synchronized(this){
            instance ?: UserRepository(userDao)
        }.also { instance = it}
    }
}

