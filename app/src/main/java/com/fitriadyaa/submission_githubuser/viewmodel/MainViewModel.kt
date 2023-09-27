package com.fitriadyaa.submission_githubuser.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.fitriadyaa.submission_githubuser.data.local.datastore.SettingPreferences
import com.fitriadyaa.submission_githubuser.data.remote.ApiConfig
import com.fitriadyaa.submission_githubuser.model.UserModel
import com.fitriadyaa.submission_githubuser.model.UserResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel :ViewModel() {
    private val listUsers = MutableLiveData<ArrayList<UserModel>>()

    fun setSearchUser(query: String) {
        val apiInstance = ApiConfig.getApiService()
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
    }

    fun getSearchUser(): LiveData<ArrayList<UserModel>> {
        return listUsers
    }
}