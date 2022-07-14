package com.itstime.haejo.api

import com.google.gson.annotations.SerializedName

data class PostApplicationDTO(
    @SerializedName("memberId")
    var memberId: Long? = null,
    @SerializedName("studyId")
    var studyId: Long? = null,
    @SerializedName("answers")
    var questions: List<AnswerDTO>
)

data class AnswerDTO(
    @SerializedName("sequence")
    var sequence: Int? = null,
    @SerializedName("question")
    var question: String? = null,
    @SerializedName("answer")
    var answer: String? = null
)

data class PostApplicationResultDTO(
    @SerializedName("studyMemberId")
    var result: Long? = null
)
