package com.jymun.usanchaengyeo.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jymun.usanchaengyeo.data.entity.history.HistoryEntity

@Database(
    entities = [HistoryEntity::class],
    version = 1
)
@TypeConverters(HistoryTypeConverters::class)
abstract class HistoryDatabase : RoomDatabase() {

    abstract fun dao(): HistoryDao
}