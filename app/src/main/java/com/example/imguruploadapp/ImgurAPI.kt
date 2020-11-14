package com.example.imguruploadapp

import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*
import java.io.File

val serverURL = "https://api.imgur.com"

interface ImgurAPI {
    @Multipart
    @POST("/3/image")
    fun postImage(
        @Header("Authorization") authorizationHeader:String,
        @Query("title") title: String,
        @Query("description") description: String,
        @Query("name") name: String,
        @Part("image") image: RequestBody

    ): Call<ResponseBody>
}

data class UploadPojo(
    val title:String,
    val description: String,
    val name: String,
    val file: File
)