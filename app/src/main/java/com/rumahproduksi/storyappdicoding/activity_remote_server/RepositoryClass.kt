package com.rumahproduksi.storyappdicoding.activity_remote_server


import com.rumahproduksi.storyappdicoding.activity_remote_server.remote_api.api_server.ApiService
import com.rumahproduksi.storyappdicoding.activity_remote_server.remote_api.response_server.login_response.LoginResponse
import com.rumahproduksi.storyappdicoding.activity_remote_server.remote_api.response_server.register_response.RegisterResponse
import com.rumahproduksi.storyappdicoding.activity_remote_server.remote_api.response_server.story_response.StoryResponse
import com.rumahproduksi.storyappdicoding.activity_remote_server.remote_api.response_server.upload_response.UploadResponse
import com.rumahproduksi.storyappdicoding.activity_utils.network.NetworkResults
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import java.io.File

class RepositoryClass constructor(private val apiService: ApiService) {

    suspend fun getStories(auth: String) : Flow<NetworkResults<StoryResponse>> =
        flow {
            try {
                val generateToken = generateAuthorization(auth)
                val response = apiService.getStories(generateToken)
                emit(NetworkResults.Success(response))
            } catch (e : Exception) {
                val ex = (e as? HttpException)?.response()?.errorBody()?.string()
                emit(NetworkResults.Error(ex.toString()))
            }
        }.flowOn(Dispatchers.IO)

    suspend fun uploadStory(auth: String, description: String, file: File) : Flow<NetworkResults<UploadResponse>> =
        flow {
            try {
                val generateToken = generateAuthorization(auth)
                val desc = description.toRequestBody("text/plain".toMediaType())
                val requestImageFile = file.asRequestBody("image/jpg".toMediaTypeOrNull())
                val imageMultipart = MultipartBody.Part.createFormData(
                    "photo",
                    file.name,
                    requestImageFile
                )
                val response = apiService.uploadStory(generateToken,imageMultipart,desc)
                emit(NetworkResults.Success(response))
            } catch (e : Exception) {
                val ex = (e as? HttpException)?.response()?.errorBody()?.string()
                emit(NetworkResults.Error(ex.toString()))
            }
        }.flowOn(Dispatchers.IO)

    suspend fun register(
        name: String,
        email: String,
        password: String
    ): Flow<NetworkResults<RegisterResponse>> = flow {
        try {
            val response = apiService.register(name, email, password)
            emit(NetworkResults.Success(response))
        } catch(e : Exception) {
            e.printStackTrace()
            emit(NetworkResults.Error(e.toString()))
        }
    }.flowOn(Dispatchers.IO)

    suspend fun login(
        email: String,
        password: String
    ): Flow<NetworkResults<LoginResponse>> = flow {
        try {
            val response = apiService.login(email, password)
            emit(NetworkResults.Success(response))
        } catch(e : Exception) {
            e.printStackTrace()
            emit(NetworkResults.Error(e.toString()))
        }
    }.flowOn(Dispatchers.IO)

    private fun generateAuthorization(token: String) : String {
        return "Bearer $token"
    }

}