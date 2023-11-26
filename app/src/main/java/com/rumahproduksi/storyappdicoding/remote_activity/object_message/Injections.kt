package com.rumahproduksi.storyappdicoding.remote_activity.object_message

import android.content.Context
import com.dicoding.picodiploma.loginwithanimation.remote.retrofit.ApiClients
import com.rumahproduksi.storyappdicoding.remote_activity.data_remote.RepositoryStory
import com.rumahproduksi.storyappdicoding.remote_activity.data_remote.RepositoryUser
import com.rumahproduksi.storyappdicoding.preference_activity.PreferenceUserManager
import com.rumahproduksi.storyappdicoding.preference_activity.dataStore

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