package com.nsnik.nrs.jaron.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TagDao {

    @Query("SELECT * FROM TagEntity")
    fun getAllTags(): LiveData<List<TagEntity>>

    @Query("SELECT * FROM TagEntity WHERE tagValue = :value")
    fun getTagByValue(value: String): LiveData<TagEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertTags(tagEntity: List<TagEntity>): LongArray

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateTags(tagEntity: List<TagEntity>): Int

    @Delete
    fun deleteTags(tagEntity: List<TagEntity>)

}