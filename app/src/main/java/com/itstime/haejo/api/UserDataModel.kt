package com.itstime.haejo.api

data class UserModel(
    var userEmail: String? = null,
    var userName: String? = null,
    var nickName: String? = null
)

data class PostResult(
    var result: Long? = null
)
