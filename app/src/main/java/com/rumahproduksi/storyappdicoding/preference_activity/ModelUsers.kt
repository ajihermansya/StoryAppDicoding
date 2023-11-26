package com.rumahproduksi.storyappdicoding.preference_activity

data class ModelUsers(
    val name: String,
    val userId: String,
    val token: String,
    val isLogin: Boolean = false
)
