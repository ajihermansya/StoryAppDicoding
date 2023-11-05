package com.rumahproduksi.storyappdicoding.activity_user.model_user

import androidx.lifecycle.ViewModel
import com.rumahproduksi.storyappdicoding.activity_remote_server.RepositoryClass

class LoginRegisterModel constructor(private val dataRepository: RepositoryClass) : ViewModel() {

    suspend fun register(name: String, email: String, password: String) = dataRepository.register(name, email, password)

    suspend fun login(email: String, password: String) = dataRepository.login(email, password)

}