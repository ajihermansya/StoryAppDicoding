package com.rumahproduksi.storyappdicoding.view_model
import androidx.lifecycle.ViewModel
import com.rumahproduksi.storyappdicoding.remote_activity.data_remote.RepositoryStory
import java.io.File

class ViewModelUploadStory(private val storyRepository: RepositoryStory) : ViewModel() {

    fun uploadImage(file: File, description: String) = storyRepository.uploadImage(file, description)
}