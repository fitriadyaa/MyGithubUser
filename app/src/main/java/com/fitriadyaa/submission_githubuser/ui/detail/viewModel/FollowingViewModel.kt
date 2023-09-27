package com.fitriadyaa.submission_githubuser.ui.detail.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fitriadyaa.submission_githubuser.data.remote.ApiConfig
import com.fitriadyaa.submission_githubuser.model.UserModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingViewModel: ViewModel() {
    val listFollowing = MutableLiveData<ArrayList<UserModel>>()

    fun setListFollowing(username: String){
        ApiConfig.getApiService().getFollowing(username)
            .enqueue(object: Callback<ArrayList<UserModel>>
            {
                override fun onResponse(
                    call: Call<ArrayList<UserModel>>,
                    response: Response<ArrayList<UserModel>>
                ) {
                    if (response.isSuccessful){
                        listFollowing.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<UserModel>>, t: Throwable) {
                    Log.d("Failure", t.message ?: "Unknown error occurred.")
                }
            }
            )
    }

    fun getListFollowing(): LiveData<ArrayList<UserModel>> {
        return listFollowing
    }
}