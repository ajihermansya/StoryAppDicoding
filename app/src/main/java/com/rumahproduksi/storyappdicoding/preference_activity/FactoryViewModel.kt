package com.rumahproduksi.storyappdicoding.preference_activity

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rumahproduksi.storyappdicoding.view_model.ViewModelLocation
import com.rumahproduksi.storyappdicoding.view_model.ViewModelLogin
import com.rumahproduksi.storyappdicoding.view_model.ViewModelMain
import com.rumahproduksi.storyappdicoding.view_model.ViewModelSignUp
import com.rumahproduksi.storyappdicoding.view_model.ViewModelStory
import com.rumahproduksi.storyappdicoding.view_model.ViewModelUploadStory
import com.rumahproduksi.storyappdicoding.remote_activity.object_message.Injections
import com.rumahproduksi.storyappdicoding.remote_activity.data_remote.RepositoryStory
import com.rumahproduksi.storyappdicoding.remote_activity.data_remote.RepositoryUser

class FactoryViewModel(private val userRepository: RepositoryUser, private val storyRepository: RepositoryStory) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(ViewModelMain::class.java) -> {
                ViewModelMain(userRepository) as T
            }
            modelClass.isAssignableFrom(ViewModelLogin::class.java) -> {
                ViewModelLogin(userRepository) as T
            }

            modelClass.isAssignableFrom(ViewModelSignUp::class.java) -> {
                ViewModelSignUp(userRepository) as T
            }
            modelClass.isAssignableFrom(ViewModelStory::class.java) -> {
                ViewModelStory(storyRepository) as T
            }
            modelClass.isAssignableFrom(ViewModelUploadStory::class.java) -> {
                ViewModelUploadStory(storyRepository) as T
            }
            modelClass.isAssignableFrom(ViewModelLocation::class.java) -> {
                ViewModelLocation(storyRepository) as T
            }
            else -> throw IllegalArgumentException("ViewModel class tidak diketahui: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: FactoryViewModel? = null
        @JvmStatic
        fun getInstance(context: Context): FactoryViewModel {
            if (INSTANCE == null) {
                synchronized(FactoryViewModel::class.java) {
                    INSTANCE = FactoryViewModel(Injections.provideRepository(context), Injections.provideStoryRepository(context))
                }
            }
            return INSTANCE as FactoryViewModel
        }
    }
}