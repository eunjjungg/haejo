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

    @GET("api/member/{id}")
    fun getUser(
        @Path("id") id: Long
    ): Call<UserGetDTO>

    @POST("api/studyMember")
    fun postApplication(
        @Body jsonParams: PostApplicationDTO
    ): Call<PostApplicationResultDTO>

    @POST("api/study")
    fun postStudy(
        @Body jsonParams: PostContentUploadDTO
    ): Call<PostStudyResultDTO>

    @POST("api/member/{id}/review")
    fun postRating(
        @Path("id") id: Long,
        @Body jsonParams: PostRatingDTO
    ): Call<PostRatingResultDTO>

    @GET("api/studyMember/study/{id}")
    fun getOnGoingPostList(
        @Path("id") id: Long
    ): Call<PostListArrayDTO>

    @GET("api/member/{id}/review")
    fun getRatingList(
        @Path("id") id: Long
    ): Call<GetRatingArrayDTO>

    @GET("api/studyMember/study/Host/{id}")
    fun getWroteStudyPostList(
        @Path("id") id: Long
    ): Call<PostListArrayDTO>

    @GET("api/study/{id}")
    fun getPostList (
        @Path("id") id: Int
    ): Call<PostListDTO>

    @GET("api/studyMember/{id}/study")
    fun getStudyMemberList(
        @Path("id") id: Long
    ): Call<StudyMemberListDTO>

    @GET("api/study/{id}")
    fun getPostContent (
        @Path("id") id: Int
    ): Call<PostContentDTO>

    companion object {
        private const val BASE_URL = "http://15.165.133.114:8080/"

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