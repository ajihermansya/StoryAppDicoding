package com.rumahproduksi.storyappdicoding.view_model

import androidx.lifecycle.ViewModel
import com.rumahproduksi.storyappdicoding.remote_activity.data_remote.RepositoryUser
import com.rumahproduksi.storyappdicoding.remote_activity.response.activity_response.ResponseRegister

class ViewModelSignUp(private val userRepository: RepositoryUser) : ViewModel(){
    suspend fun register (name: String, email: String, password: String) : ResponseRegister {
        return userRepository.registerUser(name, email, password)
    }
}