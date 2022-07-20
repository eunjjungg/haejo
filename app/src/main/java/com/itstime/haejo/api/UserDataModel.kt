package com.itstime.haejo.api

import com.google.gson.annotations.SerializedName

data class UserGetDTO(
    @SerializedName("id")
    var memberId: Long? = null,
    @SerializedName("battery")
    var battery: Int? = null,
    @SerializedName("email")
    var email: String? = null,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("nickname")
    var nickname: String? = null,
    @SerializedName("profile")
    var profile: Int? = null
)

data class UploadUserModel(
    @SerializedName("email")
    var email: String? = null,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("nickname")
    var nickname: String? = null,
    @SerializedName("profile")
    var profile: Int? = null
)

data class PostUserResult(
    @SerializedName("id")
    var result: Long? = null
)

data class StudyMemberDTO(
    @SerializedName("nickname")
    var nickname: String? = null,
    @SerializedName("grade")
    var grade: String? = null,
    @SerializedName("battery")
    var battery: Int? = null,
    @SerializedName("profile")
    var profile: Int? = null
)

data class StudyMemberListDTO(
    @SerializedName("data")
    var studyMemberList: List<StudyMemberDTO>? = null
)
