package com.itstime.haejo.api

import com.google.gson.annotations.SerializedName

//post 목록들 보일 때 필요한 DTO : get용도
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

//StudyInfo에서 내용 보일 때 필요한 DTO : get용도
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

//PostContentDTO의 questions에 들어가는 DTO
data class questionDTO(
    @SerializedName("questionId")
    var questionId: Long? = null,
    @SerializedName("question")
    var question: String? = null
)

//Study 생성 시 api와의 통신을 위해 필요한 DTO
data class PostContentUploadDTO(
    @SerializedName("memberId")
    var memberId: Long?,
    @SerializedName("title")
    var title: String? = null,
    @SerializedName("region")
    var region: String? = null,
    @SerializedName("dayOfWeek")
    var week: String? = null,
    @SerializedName("isOnline")
    var isUntact: String? = null,
    @SerializedName("categories")
    var category: String? = null,
    @SerializedName("personLimit")
    var personLimit: Int? = null,
    @SerializedName("content")
    var content: String? = null,
    @SerializedName("notification")
    var notification: String? = null,
    @SerializedName("questions")
    var questions: List<questionsUploadDTO>? = null
)

data class questionsUploadDTO(
    @SerializedName("question")
    var question: String? = null
)

//Study 생성 메소드인 postStudy의 반환 데이터 모델
data class PostStudyResultDTO(
    @SerializedName("study_id")
    var studyId: Long? = null
)

