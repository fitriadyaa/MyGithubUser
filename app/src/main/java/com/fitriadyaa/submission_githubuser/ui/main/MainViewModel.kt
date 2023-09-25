package com.fitriadyaa.submission_githubuser.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fitriadyaa.submission_githubuser.data.remote.ApiConfig
import com.fitriadyaa.submission_githubuser.model.UserModel
import com.fitriadyaa.submission_githubuser.model.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    private val listUsers = MutableLiveData<ArrayList<UserModel>>()

    fun setSearchUser(query: String) {
        val apiInstance = ApiConfig.apiInstance
        if (apiInstance != null) {
            apiInstance.getSearchUser(query).enqueue(object : Callback<UserResponse> {
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    if (response.isSuccessful) {
                        listUsers.postValue(response.body()?.items)
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    Log.d("Failure", t.message ?: "Unknown error occurred.")
                }
            })
        } else {
            Log.d("ApiInstance", "Api instance is null.")
        }
    }

    fun getSearchUser(): LiveData<ArrayList<UserModel>> {
        return listUsers
    }
}