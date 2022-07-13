package com.usanchaengyeo.usanchaengyeo.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.usanchaengyeo.usanchaengyeo.data.model.address.Address

@Database(
    entities = [Address::class],
    version = 1
)
abstract class SearchHistoryDatabase : RoomDatabase() {

    abstract fun historyDao(): SearchHistoryDao
}