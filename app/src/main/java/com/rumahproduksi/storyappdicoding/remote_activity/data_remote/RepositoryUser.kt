package com.rumahproduksi.storyappdicoding.remote_activity.data_remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.rumahproduksi.storyappdicoding.remote_activity.retrofit_remote.ApiServices
import com.rumahproduksi.storyappdicoding.remote_activity.response.activity_response.ResponseLogin
import com.rumahproduksi.storyappdicoding.remote_activity.response.activity_response.ResponseRegister
import com.rumahproduksi.storyappdicoding.preference_activity.ModelUsers
import com.rumahproduksi.storyappdicoding.preference_activity.PreferenceUserManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class RepositoryUser private constructor(
    private val apiService: ApiServices,
    private val userPreference: PreferenceUserManager
) : CoroutineScope {

    fun saveSession(user: ModelUsers) {
        launch(Dispatchers.IO) {
            userPreference.saveSession(user)
        }
    }

    fun getSession(): Flow<ModelUsers> {
        return userPreference.getSession()
    }

    suspend fun logout() {
        userPreference.logout()
    }

    suspend fun registerUser(name: String, email: String, password: String) : ResponseRegister {
        return apiService.register(name, email, password)
    }

    fun loginUser(email: String, password: String) : LiveData<Results<ResponseLogin>> = liveData {
        emit(Results.Loading)
        try {
            val result = apiService.login(email, password)
            if (result.error == false) {
                emit(Results.Success(result))
            } else {
                emit(Results.Error(result.message.toString()))
            }
        } catch (e: Exception) {
            emit(Results.Error(e.message.toString()))
        }
    }


    companion object {
        @Volatile
        private var instance: RepositoryUser? = null
        fun getInstance(
            apiService: ApiServices,
            userPreference: PreferenceUserManager
        ): RepositoryUser =
            instance ?: synchronized(this) {
                instance ?: RepositoryUser(apiService, userPreference)
            }.also { instance = it }

        fun clearInstance() {
            RepositoryStory.instance = null
        }
    }

    override val coroutineContext: CoroutineContext get() = Dispatchers.Main
}