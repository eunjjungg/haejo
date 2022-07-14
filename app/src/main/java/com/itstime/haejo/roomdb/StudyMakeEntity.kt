package com.itstime.haejo.roomdb

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class StudyMakeEntity(
    @PrimaryKey(autoGenerate = false)
    var memberId: Long,
    var title: String?,
    var region: String?,
    var dayOfWeek: String?,
    var isOnline: String?,
    var categories: String?,
    var personLimit: Int?,
    var content: String?,
    var question0: String?,
    var question1: String?,
    var question2: String?,
    var notification: String?
)
