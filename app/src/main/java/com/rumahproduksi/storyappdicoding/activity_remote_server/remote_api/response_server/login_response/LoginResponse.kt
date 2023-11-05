package com.rumahproduksi.storyappdicoding.activity_remote_server.remote_api.response_server.login_response

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("loginResult")
    val result: ResultsLogin

)