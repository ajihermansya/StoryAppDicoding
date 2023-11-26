package com.rumahproduksi.storyappdicoding.view_model

import androidx.lifecycle.ViewModel
import com.rumahproduksi.storyappdicoding.remote_activity.data_remote.RepositoryStory


class ViewModelLocation(private val storyRepository: RepositoryStory) : ViewModel() {
    fun StoriesWithLocation() = storyRepository.getStoriesWithLocation()
}