package com.fitriadyaa.submission_githubuser.model

import com.google.gson.annotations.SerializedName

data class UserDetailResponse(
    @field:SerializedName("bio")
    val bio: String?,

    @field:SerializedName("login")
    val login: String,

    @field:SerializedName("blog")
    val blog: String?,

    @field:SerializedName("followers")
    val followers: Int,

    @field:SerializedName("avatar_url")
    val avatarUrl: String,

    @field:SerializedName("html_url")
    val htmlUrl: String,

    @field:SerializedName("following")
    val following: Int,

    @field:SerializedName("name")
    val name: String?,

    @field:SerializedName("company")
    val company: String?,

    @field:SerializedName("location")
    val location: String?,

    @field:SerializedName("public_repos")
    val publicRepos: Int,
)
