package com.rumahproduksi.storyappdicoding.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.rumahproduksi.storyappdicoding.preference_activity.ModelUsers
import com.rumahproduksi.storyappdicoding.remote_activity.data_remote.RepositoryStory
import com.rumahproduksi.storyappdicoding.remote_activity.response.activity_response.ListStoryItem
import kotlinx.coroutines.launch


class ViewModelStory(private val storyRepository: RepositoryStory) : ViewModel() {

    val getStory: LiveData<PagingData<ListStoryItem>> =
        storyRepository.getStory().cachedIn(viewModelScope)

    fun getSession(): LiveData<ModelUsers> {
        return storyRepository.getSession().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            storyRepository.logout()
        }

    }
}