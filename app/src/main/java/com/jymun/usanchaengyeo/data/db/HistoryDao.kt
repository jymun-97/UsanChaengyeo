package com.jymun.usanchaengyeo.data.db

import androidx.room.*
import com.jymun.usanchaengyeo.data.entity.history.HistoryEntity

@Dao
interface HistoryDao {

    @Query("SELECT * FROM history ORDER BY isPinned DESC, timestamp DESC")
    suspend fun loadAllHistory(): List<HistoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addHistory(historyEntity: HistoryEntity)

    @Delete
    suspend fun deleteHistory(historyEntity: HistoryEntity)
}