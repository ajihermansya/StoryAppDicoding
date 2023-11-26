package com.rumahproduksi.storyappdicoding.view_model

import androidx.lifecycle.ViewModel
import com.rumahproduksi.storyappdicoding.preference_activity.ModelUsers
import com.rumahproduksi.storyappdicoding.remote_activity.data_remote.RepositoryUser


class ViewModelLogin(private val repository: RepositoryUser) : ViewModel() {
    fun saveSession(user: ModelUsers) {
        repository.saveSession(user)
    }

    fun login(email: String, password: String) = repository.loginUser(email, password)
}