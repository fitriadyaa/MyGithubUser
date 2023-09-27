package com.fitriadyaa.submission_githubuser.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
class User{
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    var name: String? = null
    var isFavorite = false
}
