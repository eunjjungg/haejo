package com.itstime.haejo.roomdb

import androidx.room.*

@Dao
interface StudyMakeDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: StudyMakeEntity)

    @Query("SELECT * FROM StudyMakeEntity WHERE memberId == :id")
    fun getStudyMakeEntitiy(id: Long) : StudyMakeEntity

    @Update
    fun updateStudyMakeEntity(studyEntity: StudyMakeEntity)
}