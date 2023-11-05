package com.rumahproduksi.storyappdicoding.activity_remote_server.remote_api.response_server.login_response

import com.google.gson.annotations.SerializedName

data class ResultsLogin(
    @field:SerializedName("userId")
    val userId: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("token")
    val token: String,
)
