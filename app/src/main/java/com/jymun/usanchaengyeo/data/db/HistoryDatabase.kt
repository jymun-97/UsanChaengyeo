package com.jymun.usanchaengyeo.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jymun.usanchaengyeo.data.entity.history.HistoryEntity

@Database(
    entities = [HistoryEntity::class],
    version = 1
)
abstract class HistoryDatabase : RoomDatabase() {

    abstract fun dao(): HistoryDao
}