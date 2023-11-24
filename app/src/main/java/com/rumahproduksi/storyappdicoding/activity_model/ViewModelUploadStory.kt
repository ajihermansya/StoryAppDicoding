package com.rumahproduksi.storyappdicoding.activity_model
import androidx.lifecycle.ViewModel
import com.rumahproduksi.storyappdicoding.activity_remote.data_remote.RepositoryStory
import java.io.File

class ViewModelUploadStory(private val storyRepository: RepositoryStory) : ViewModel() {

    fun uploadImage(file: File, description: String) = storyRepository.uploadImage(file, description)
}