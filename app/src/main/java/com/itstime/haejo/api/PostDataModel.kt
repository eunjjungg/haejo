package com.itstime.haejo.api

import com.google.gson.annotations.SerializedName

data class PostListDTO(
    @SerializedName("studyId")
    var studyId: Long? = null,
    @SerializedName("postTime")
    var postTime: String? = null,
    @SerializedName("isOnline")
    var isUntact: String? = null,
    @SerializedName("region")
    var region: String? = null,
    @SerializedName("title")
    var title: String? = null
)

data class PostContentDTO(
    @SerializedName("studyId")
    var studyId: Long? = null,
    @SerializedName("postTime")
    var postTime: String? = null,
    @SerializedName("isOnline")
    var isUntact: String? = null,
    @SerializedName("region")
    var region: String? = null,
    @SerializedName("title")
    var title: String? = null,
    @SerializedName("dayOfWeek")
    var week: String? = null,
    @SerializedName("personLimit")
    var personLimit: Int? = null,
    @SerializedName("content")
    var content: String? = null,
    @SerializedName("status")
    var status: String? = null,
    @SerializedName("categories")
    var category: String? = null,
    @SerializedName("questions")
    var questions: List<questionDTO>
)

data class questionDTO(
    @SerializedName("questionId")
    var questionId: Long? = null,
    @SerializedName("question")
    var question: String? = null
)

