package com.rumahproduksi.storyappdicoding.activity_remote_server.remote_api.response_server.story_response

import com.google.gson.annotations.SerializedName

data class StoryResponse(
    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("listStory")
    val listStory: List<StoryList>,
)
