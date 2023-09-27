package com.fitriadyaa.submission_githubuser.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.fitriadyaa.submission_githubuser.data.local.datastore.SettingPreferences
import com.fitriadyaa.submission_githubuser.data.local.room.UserDatabase
import com.fitriadyaa.submission_githubuser.data.remote.ApiConfig
import com.fitriadyaa.submission_githubuser.repository.UserRepository

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name="settings")
object Injection {
    fun provideRepository(context: Context): UserRepository {
        val apiService = ApiConfig.getApiService()
        val database = UserDatabase.getDatabase(context)
        val dao = database.UserDao()
        return UserRepository.getInstance(dao)
    }

    fun providePreferences(context: Context): SettingPreferences {
        return SettingPreferences.getInstance(context.dataStore)
    }
}