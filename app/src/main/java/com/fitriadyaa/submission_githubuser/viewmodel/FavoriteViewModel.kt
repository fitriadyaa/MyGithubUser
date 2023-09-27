package com.fitriadyaa.submission_githubuser.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.fitriadyaa.submission_githubuser.data.local.entity.UserEntity
import com.fitriadyaa.submission_githubuser.data.local.room.UserDatabase
import com.fitriadyaa.submission_githubuser.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {
    private val userRepository: UserRepository
    init {
        val userDb = UserDatabase.getDatabase(application)!!
        userRepository = UserRepository(userDb.UserDao())
    }

    suspend fun check(id: Int): Int {
        return withContext(Dispatchers.IO) {
            userRepository.check(id)
        }
    }

    suspend fun saveFavorite(username: String, avatar: String, id: Int) {
        withContext(Dispatchers.IO) {
            userRepository.setFavorite(username, avatar, id, true)
        }
    }

    suspend fun deleteFavorite(id: Int) {
        withContext(Dispatchers.IO) {
            userRepository.deleteFavorite(id)
        }
    }

    fun getFavoriteUsers(): LiveData<List<UserEntity>> {
        return userRepository.getFavoriteUser()
    }
}

