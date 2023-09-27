package com.fitriadyaa.submission_githubuser.model

import com.google.gson.annotations.SerializedName

data class UserModel(

    @field:SerializedName("id")
    val id : Int,

    @field:SerializedName("avatar_url")
    val avatarUrl: String,

    @field:SerializedName("login")
    val login: String?,
)
