package com.itstime.haejo.api

import com.google.gson.annotations.SerializedName

data class UserModel(
    @SerializedName("id")
    var memberId: Long? = null,
    @SerializedName("battery")
    var battery: Int? = null,
    @SerializedName("email")
    var email: String? = null,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("nickname")
    var nickname: String? = null
)

data class UploadUserModel(
    @SerializedName("email")
    var email: String? = null,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("nickname")
    var nickname: String? = null,
)

data class PostResult(
    var result: Long? = null
)
