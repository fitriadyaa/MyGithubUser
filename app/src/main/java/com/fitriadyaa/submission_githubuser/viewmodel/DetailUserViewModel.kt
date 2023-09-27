package com.fitriadyaa.submission_githubuser.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fitriadyaa.submission_githubuser.data.local.entity.User
import com.fitriadyaa.submission_githubuser.data.remote.ApiConfig
import com.fitriadyaa.submission_githubuser.model.UserDetailResponse
import com.fitriadyaa.submission_githubuser.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel(private val userRepository: UserRepository) : ViewModel() {
    val user = MutableLiveData<UserDetailResponse>()

    fun setUserDetail(username: String) {
        ApiConfig.getApiService().getUserDetail(username)
            .enqueue(object : Callback<UserDetailResponse> {
                override fun onResponse(
                    call: Call<UserDetailResponse>,
                    response: Response<UserDetailResponse>
                ) {
                    if (response.isSuccessful) {
                        user.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<UserDetailResponse>, t: Throwable) {
                    Log.d("Failure", t.message ?: "Unknown error occurred.")
                }

            })
    }

    fun getUserDetail(): LiveData<UserDetailResponse> {
        return user
    }

    private val _favoriteUser = MutableLiveData<User?>()

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean>
        get() = _isFavorite

    fun checkIsFavorite(username: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val user = userRepository.getFavoriteUserByUsername(username)
            _isFavorite.postValue(user != null)
            _favoriteUser.postValue(user.value)
        }
    }

    fun toggleFavoriteStatus(username: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val user = userRepository.getFavoriteUserByUsername(username)

            if (user == null) {
                // User is not a favorite, insert them as a favorite
                val insertedUser = User()
                userRepository.insert(insertedUser)
                _isFavorite.postValue(true)
                // Update favoriteUser LiveData with the inserted user
                _favoriteUser.postValue(insertedUser)
            } else {
                // User is already a favorite, remove them from favorites
                userRepository.delete(user = User())
                _isFavorite.postValue(false)
                // Update favoriteUser LiveData to null (user removed)
                _favoriteUser.postValue(null)
            }
        }
    }
}
