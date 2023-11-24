package com.rumahproduksi.storyappdicoding.activity_model

import androidx.lifecycle.ViewModel
import com.rumahproduksi.storyappdicoding.activity_remote.data_remote.RepositoryStory


class ViewModelLocation(private val storyRepository: RepositoryStory) : ViewModel() {
    fun StoriesWithLocation() = storyRepository.getStoriesWithLocation()
}