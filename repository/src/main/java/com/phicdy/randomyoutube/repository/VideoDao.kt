package com.phicdy.randomyoutube.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
internal interface VideoDao {
    @Query("SELECT * FROM video_entries")
    fun getAll(): Flow<List<VideoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(videoEntity: List<VideoEntity>)
}