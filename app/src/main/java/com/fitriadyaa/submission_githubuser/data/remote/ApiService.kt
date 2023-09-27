package com.fitriadyaa.submission_githubuser.data.remote

import com.fitriadyaa.submission_githubuser.BuildConfig
import com.fitriadyaa.submission_githubuser.model.UserDetailResponse
import com.fitriadyaa.submission_githubuser.model.UserModel
import com.fitriadyaa.submission_githubuser.model.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun getSearchUser(
        @Query("q") query: String
    ): Call<UserResponse>

    @GET("users/{username}")
    fun getUserDetail(
        @Path("username") username: String
    ): Call<UserDetailResponse>

    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String
    ): Call<ArrayList<UserModel>>

    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String
    ): Call<ArrayList<UserModel>>
}