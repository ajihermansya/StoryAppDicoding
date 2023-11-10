package com.rumahproduksi.storyappdicoding.activity_remote_server.remote_api.api_server

import com.rumahproduksi.storyappdicoding.activity_remote_server.remote_api.response_server.login_response.LoginResponse
import com.rumahproduksi.storyappdicoding.activity_remote_server.remote_api.response_server.register_response.RegisterResponse
import com.rumahproduksi.storyappdicoding.activity_remote_server.remote_api.response_server.story_response.StoryResponse
import com.rumahproduksi.storyappdicoding.activity_remote_server.remote_api.response_server.upload_response.UploadResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiService {

    @POST("register")
    @FormUrlEncoded
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ) : RegisterResponse

    @POST("login")
    @FormUrlEncoded
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ) : LoginResponse

    @GET("stories")
    suspend fun getStories(
        @Header("Authorization") auth: String,
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 20,
        @Query("position") location: Int? = null
    ) : StoryResponse

    @Multipart
    @POST("stories")
    suspend fun uploadStory(
        @Header("Authorization") auth: String,
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody
    ) : UploadResponse


}