package com.phicdy.randomyoutube.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
internal interface VideoDao {
    @Query("SELECT * FROM video_entries")
    fun getAll(): List<VideoEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(videoEntity: List<VideoEntity>)
}