package com.example.imguruploadapp

import android.util.Log
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UploadService {
    private val TAG = this::class.java.simpleName
    private val retrofit = Retrofit.Builder()
        .baseUrl(serverURL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun uploadImage(image: UploadPojo) {
        val requestFile: RequestBody =
            RequestBody.create(MediaType.parse("image/jpeg"), image.file)

        GlobalScope.launch {
            retrofit.create(ImgurAPI::class.java)
                .postImage(
                    getAuthorizationHeader(),
                    image.title,
                    image.description,
                    image.name,
                    requestFile
                ).enqueue(object : Callback<ResponseBody> {
                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Log.d(TAG, call.toString())
                    }

                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ) {
                        Log.d(TAG, response.toString())
                    }
                })
        }

    }

}