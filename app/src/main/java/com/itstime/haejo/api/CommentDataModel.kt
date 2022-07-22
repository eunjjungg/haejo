package com.itstime.haejo.api

import com.google.gson.annotations.SerializedName

data class CommentUploadDTO(
    @SerializedName("comment")
    var comment: String? = null,
    @SerializedName("groupNum")
    var groupNum: Int? = null,
    @SerializedName("layer")
    var layer: Int? = null,
    @SerializedName("sequence")
    var sequence: Int? = null
)

data class CommentDTO(
    @SerializedName("comment")
    var comment: String? = null,
    @SerializedName("groupNum")
    var groupNum: Int? = null,
    @SerializedName("layer")
    var layer: Int? = null,
    @SerializedName("sequence")
    var sequence: Int? = null,
    @SerializedName("profile")
    var profile: Int? = null,
    @SerializedName("nickname")
    var nickname: String? = null,
    @SerializedName("memberId")
    var memberId: Long? = null
)
