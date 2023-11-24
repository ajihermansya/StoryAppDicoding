package com.rumahproduksi.storyappdicoding.activity_remote.message

import android.content.Context
import com.dicoding.picodiploma.loginwithanimation.remote.retrofit.ApiClients
import com.rumahproduksi.storyappdicoding.activity_remote.data_remote.RepositoryStory
import com.rumahproduksi.storyappdicoding.activity_remote.data_remote.RepositoryUser
import com.rumahproduksi.storyappdicoding.activity_preferences.PreferenceUserManager
import com.rumahproduksi.storyappdicoding.activity_preferences.dataStore

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injections {
    fun provideRepository(context: Context): RepositoryUser {
        val pref = PreferenceUserManager.getInstance(context.dataStore)
        val user = runBlocking { pref.getSession().first() }
        val apiService = ApiClients.getApiService(user.token)
        return RepositoryUser.getInstance(apiService, pref)
    }

    fun provideStoryRepository(context: Context): RepositoryStory {
        val pref = PreferenceUserManager.getInstance(context.dataStore)
        val user = runBlocking { pref.getSession().first() }
        val apiService = ApiClients.getApiService(user.token)
        return RepositoryStory.getInstance(apiService, pref)
    }
}