package com.rumahproduksi.storyappdicoding.remote_activity.response.activity_response

import com.google.gson.annotations.SerializedName

data class ResponseRegister(
    @field:SerializedName("error")
    val error: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null
)
