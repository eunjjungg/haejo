package com.itstime.haejo.api

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface APIS {

    @POST("/user")
    fun post_user(
        @Body jsonParams: UserModel
    ): Call<PostResult>

    companion object {
        private const val BASE_URL = ""

        fun create(): APIS {
            val gson: Gson = GsonBuilder().setLenient().create()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(APIS::class.java)
        }
    }
}