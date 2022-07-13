package com.usanchaengyeo.usanchaengyeo.data.db

import androidx.room.*
import com.usanchaengyeo.usanchaengyeo.data.model.address.Address
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchHistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistory(address: Address)

    @Delete
    suspend fun deleteHistory(address: Address)

    @Query("SELECT * FROM history")
    fun loadHistory(): Flow<List<Address>>
}