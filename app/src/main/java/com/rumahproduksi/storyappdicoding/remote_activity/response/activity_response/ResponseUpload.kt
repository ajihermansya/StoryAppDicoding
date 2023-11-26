package com.rumahproduksi.storyappdicoding.remote_activity.response.activity_response

import com.google.gson.annotations.SerializedName

data class ResponseUpload (
    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String
)