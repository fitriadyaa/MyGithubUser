package com.fitriadyaa.submission_githubuser.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fitriadyaa.submission_githubuser.data.local.entity.UserEntity

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(user: UserEntity)

    @Query("DELETE from users where users.id = :id")
    fun delete(id: Int): Int

    @Query("SELECT count(*) from users where users.id = :id")
    fun check(id: Int): Int

    @Query("SELECT * from users where isFavorite = 1")
    fun getAllFavorite(): LiveData<List<UserEntity>>

    @Query("SELECT EXISTS(SELECT * FROM users WHERE username = :username)")
    fun isFavorite(username: String): Boolean
}

