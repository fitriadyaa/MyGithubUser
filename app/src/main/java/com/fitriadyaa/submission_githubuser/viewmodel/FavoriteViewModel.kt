package com.fitriadyaa.submission_githubuser.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.fitriadyaa.submission_githubuser.data.local.entity.User
import com.fitriadyaa.submission_githubuser.data.local.room.UserDatabase
import com.fitriadyaa.submission_githubuser.repository.UserRepository

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {
    private val userRepository: UserRepository
    val favoriteUsers: LiveData<List<User>>

    init {
        val context: Context = application.applicationContext
        userRepository = UserRepository(context)
        favoriteUsers = userRepository.getAllFavoriteUser()
    }
}

