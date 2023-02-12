package com.phicdy.randomyoutube.repository

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [VideoEntity::class], version = 1)
internal abstract class AppDatabase : RoomDatabase() {
    abstract fun videoDao(): VideoDao
}