package com.itstime.haejo.api

import com.google.gson.annotations.SerializedName

data class PostRatingDTO(
    @SerializedName("senderId")
    var senderId: Long? = null,
    @SerializedName("star")
    var star: Int? = null
)

data class PostRatingResultDTO(
    @SerializedName("memberId")
    var memberId: Long? = null
)

data class GetRatingDTO(
    @SerializedName("nickname")
    var nickname: String? = null,
    @SerializedName("star")
    var star: Int? = null,
    @SerializedName("sender_id")
    var senderId: Long? = null
)

data class GetRatingArrayDTO(
    @SerializedName("data")
    var postListDTO: List<GetRatingDTO>? = null
)
