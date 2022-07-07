package com.itstime.haejo.api

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.*

interface APIS {

    @POST("api/member")
    fun postUser(
        @Body jsonParams: UploadUserModel
    ): Call<PostUserResult>

    @GET("api/member/1")
    fun getUserTest(
    ): Call<UserModel>

    @GET("api/study/{id}")
    fun getPostList (
        @Path("id") id: Int
    ): Call<PostListDTO>

    @GET("api/study/{id}")
    fun getPostContent (
        @Path("id") id: Int
    ): Call<PostContentDTO>

    companion object {
        private const val BASE_URL = "http://15.165.230.222:8080/"

        fun create(): APIS {
            val gson: Gson = GsonBuilder().setLenient().create()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(APIS::class.java)
        }
    }
}