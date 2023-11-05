package com.rumahproduksi.storyappdicoding.activity_remote_server.remote_api.response_server.register_response

import com.google.gson.annotations.SerializedName

data class RegisterResponse(

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String,
)
