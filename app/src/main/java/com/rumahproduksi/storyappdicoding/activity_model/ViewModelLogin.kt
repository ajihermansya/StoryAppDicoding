package com.rumahproduksi.storyappdicoding.activity_model

import androidx.lifecycle.ViewModel
import com.rumahproduksi.storyappdicoding.activity_preferences.ModelUsers
import com.rumahproduksi.storyappdicoding.activity_remote.data_remote.RepositoryUser


class ViewModelLogin(private val repository: RepositoryUser) : ViewModel() {
    fun saveSession(user: ModelUsers) {
        repository.saveSession(user)
    }

    fun login(email: String, password: String) = repository.loginUser(email, password)
}