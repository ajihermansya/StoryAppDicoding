package com.rumahproduksi.storyappdicoding.activity_utils.model_view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rumahproduksi.storyappdicoding.activity_remote_server.RepositoryClass
import com.rumahproduksi.storyappdicoding.activity_user.model_user.HomeModel
import com.rumahproduksi.storyappdicoding.activity_user.model_user.LoginRegisterModel
import com.rumahproduksi.storyappdicoding.activity_user.model_user.StoryUploadModel


@Suppress("UNCHECKED_CAST")
class FactoryViewModel constructor(private val dataRepository: RepositoryClass) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginRegisterModel::class.java) -> {
                LoginRegisterModel(dataRepository) as T
            }
            modelClass.isAssignableFrom(HomeModel::class.java) -> {
                HomeModel(dataRepository) as T
            }
            modelClass.isAssignableFrom(StoryUploadModel::class.java) -> {
                StoryUploadModel(dataRepository) as T
            }
            else -> {
                throw IllegalArgumentException("Class ViewModel not Implement")
            }
        }
    }

}