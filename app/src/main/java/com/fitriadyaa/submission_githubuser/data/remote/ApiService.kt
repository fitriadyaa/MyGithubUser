package com.fitriadyaa.submission_githubuser.data.remote

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
    @Headers("Authorization: token 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")
    fun getSearchUser(
        @Query("q") query: String
    ): Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getUserDetail(
        @Path("username") username: String
    ): Call<UserDetailResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getFollowers(
        @Path("username") username: String
    ): Call<ArrayList<UserModel>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getFollowing(
        @Path("username") username: String
    ): Call<ArrayList<UserModel>>
}