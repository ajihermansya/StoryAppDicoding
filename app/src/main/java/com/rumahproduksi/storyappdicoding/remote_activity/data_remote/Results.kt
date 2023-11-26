package com.rumahproduksi.storyappdicoding.remote_activity.data_remote

sealed class Results<out R> private constructor() {
    data class Success<out T>(val result: T) : Results<T>()
    data class Error(val error: String) : Results<Nothing>()
    object Loading : Results<Nothing>()
}
