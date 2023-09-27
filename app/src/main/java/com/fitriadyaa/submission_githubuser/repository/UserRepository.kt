package com.fitriadyaa.submission_githubuser.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.fitriadyaa.submission_githubuser.data.local.entity.User
import com.fitriadyaa.submission_githubuser.data.local.room.UserDao
import com.fitriadyaa.submission_githubuser.data.local.room.UserDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class UserRepository(context: Context) {
    private val mUserDao: UserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = UserDatabase.getDatabase(context)
        mUserDao = db.userDao()
    }

    fun getAllFavoriteUser(): LiveData<List<User>> = mUserDao.getAllUsers()

    fun getFavoriteUserByUsername(username: String): LiveData<User> =
        mUserDao.getFavoriteUserByUsername(username)

    fun insert(user: User) {
        executorService.execute { mUserDao.insert(user) }
    }

    fun delete(user: User) {
        executorService.execute { mUserDao.delete(user) }
    }

    fun update(user: User) {
        executorService.execute { mUserDao.update(user) }
    }
}
