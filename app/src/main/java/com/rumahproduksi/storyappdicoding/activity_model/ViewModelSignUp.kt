package com.rumahproduksi.storyappdicoding.activity_model

import androidx.lifecycle.ViewModel
import com.rumahproduksi.storyappdicoding.activity_remote.data_remote.RepositoryUser
import com.rumahproduksi.storyappdicoding.activity_remote.response.ResponseRegister

class ViewModelSignUp(private val userRepository: RepositoryUser) : ViewModel(){
    suspend fun register (name: String, email: String, password: String) : ResponseRegister {
        return userRepository.registerUser(name, email, password)
    }
}