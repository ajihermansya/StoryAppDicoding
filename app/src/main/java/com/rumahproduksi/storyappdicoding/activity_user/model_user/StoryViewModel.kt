package com.rumahproduksi.storyappdicoding.activity_user.model_user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumahproduksi.storyappdicoding.activity_remote_server.RepositoryClass
import com.rumahproduksi.storyappdicoding.activity_remote_server.remote_api.response_server.story_response.StoryResponse
import com.rumahproduksi.storyappdicoding.activity_utils.network.NetworkResults
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class StoryViewModel constructor(private val repository: RepositoryClass) : ViewModel() {
    private val listStory = MutableLiveData<NetworkResults<StoryResponse>>()
    private var job: Job? = null

    fun fetchListStory(auth: String) {
        job = viewModelScope.launch {
            repository.getStories(auth).collectLatest {
                listStory.value = it
            }
        }
    }

    val responseListStory: LiveData<NetworkResults<StoryResponse>> = listStory


    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

}