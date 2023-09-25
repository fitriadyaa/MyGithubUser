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

class FollowersViewModel: ViewModel() {
    val listFollowers = MutableLiveData<ArrayList<UserModel>>()

    fun setListFollowers(username: String){
        ApiConfig.apiInstance?.getFollowers(username)
            ?.enqueue(object: Callback<ArrayList<UserModel>>
            {
                override fun onResponse(
                    call: Call<ArrayList<UserModel>>,
                    response: Response<ArrayList<UserModel>>
                ) {
                    if (response.isSuccessful){
                        listFollowers.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<UserModel>>, t: Throwable) {
                    Log.d("Failure", t.message ?: "Unknown error occurred.")
                }
            }
            )
    }

    fun getListFollowers(): LiveData<ArrayList<UserModel>> {
        return listFollowers
    }
}