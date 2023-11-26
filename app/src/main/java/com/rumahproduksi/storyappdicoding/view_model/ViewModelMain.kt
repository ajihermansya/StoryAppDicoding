package com.rumahproduksi.storyappdicoding.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.rumahproduksi.storyappdicoding.preference_activity.ModelUsers
import com.rumahproduksi.storyappdicoding.remote_activity.data_remote.RepositoryUser
import kotlinx.coroutines.launch

class ViewModelMain(private val repository: RepositoryUser) : ViewModel() {
    fun getSession(): LiveData<ModelUsers> {
        return repository.getSession().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }

}