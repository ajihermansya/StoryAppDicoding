package com.rumahproduksi.storyappdicoding.activity_remote.retrofit_remote


import com.rumahproduksi.storyappdicoding.activity_remote.response.ResponseLogin
import com.rumahproduksi.storyappdicoding.activity_remote.response.ResponseRegister
import com.rumahproduksi.storyappdicoding.activity_remote.response.ResponseStory
import com.rumahproduksi.storyappdicoding.activity_remote.response.ResponseUpload
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface ApiServices {
    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): ResponseRegister

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): ResponseLogin

    @GET("stories")
    suspend fun getStory(
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 20
    ) : ResponseStory

    @Multipart
    @POST("stories")
    suspend fun uploadStory(
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
    ): ResponseUpload

    @GET("stories")
    suspend fun getStoriesWithLocation(
        @Query("location") location: Int = 1,
    ): ResponseStory
}