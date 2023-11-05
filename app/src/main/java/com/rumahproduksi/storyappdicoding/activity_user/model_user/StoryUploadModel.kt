package com.rumahproduksi.storyappdicoding.activity_user.model_user

import androidx.lifecycle.ViewModel
import com.rumahproduksi.storyappdicoding.activity_remote_server.RepositoryClass
import java.io.File

class StoryUploadModel constructor(private val dataRepository: RepositoryClass) : ViewModel() {

    suspend fun uploadStory(auth: String, description: String, file: File) =
        dataRepository.uploadStory(auth, description, file)
}