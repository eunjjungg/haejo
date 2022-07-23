package com.itstime.haejo.api

import com.google.gson.annotations.SerializedName

data class PostRatingDTO(
    @SerializedName("senderId")
    var sender_id: Long? = null,
    @SerializedName("star")
    var start: Int? = null
)

data class PostRatingResultDTO(
    @SerializedName("memberId")
    var memberId: Long? = null
)

data class GetRatingDTO(
    @SerializedName("memberId")
    var memberId: Long? = null
)
