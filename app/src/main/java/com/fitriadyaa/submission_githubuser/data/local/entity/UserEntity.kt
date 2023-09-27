package com.fitriadyaa.submission_githubuser.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "users")
@Parcelize
data class UserEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "username")
    var username: String?,

    @ColumnInfo(name = "avatarUrl")
    var avatarUrl: String?,

    @field:ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean
) : Parcelable
