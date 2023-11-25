package com.rumahproduksi.storyappdicoding.activity_remote.data_remote
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.rumahproduksi.storyappdicoding.activity_remote.retrofit_remote.ApiServices
import com.google.gson.Gson
import com.rumahproduksi.storyappdicoding.activity_remote.response.ListStoryItem
import com.rumahproduksi.storyappdicoding.activity_remote.response.ResponseStory
import com.rumahproduksi.storyappdicoding.activity_remote.response.ResponseUpload
import com.rumahproduksi.storyappdicoding.activity_preferences.ModelUsers
import com.rumahproduksi.storyappdicoding.activity_preferences.PreferenceUserManager
import kotlinx.coroutines.flow.Flow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import timber.log.Timber
import java.io.File

class RepositoryStory private constructor(
    private val apiService: ApiServices,
    private val userPreference: PreferenceUserManager
) {
    fun getStory() : LiveData<PagingData<ListStoryItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                PagingSource(apiService)
            }
        ).liveData
    }

    fun getStoriesWithLocation(): LiveData<Results<ResponseStory>> = liveData {
        emit(Results.Loading)
        try {
            val response = apiService.getStoriesWithLocation(1)
            emit(Results.Success(response))
        } catch (e: Exception) {
            Timber.tag("ListStoryViewModel")
                .d("getStoriesWithLocation: " + e.message.toString() + " ")
            emit(Results.Error(e.message.toString()))
        }
    }

    fun getSession(): Flow<ModelUsers> {
        return userPreference.getSession()
    }

    suspend fun logout() {
        userPreference.logout()
    }

    fun uploadImage(imageFile: File, description: String) = liveData {
        emit(Results.Loading)
        val requestBody = description.toRequestBody("text/plain".toMediaType())
        val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
        val multipartBody = MultipartBody.Part.createFormData(
            "photo",
            imageFile.name,
            requestImageFile
        )
        try {
            val successResponse = apiService.uploadStory(multipartBody, requestBody)
            emit(Results.Success(successResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, ResponseUpload::class.java)
            emit(Results.Error(errorResponse.message))
        }

    }


    companion object {
        @Volatile
        var instance: RepositoryStory? = null
        fun getInstance(
            apiService: ApiServices,
            userPreference: PreferenceUserManager
        ): RepositoryStory =
            instance ?: synchronized(this) {
                instance ?: RepositoryStory(apiService, userPreference)
            }.also { instance = it }
    }
}