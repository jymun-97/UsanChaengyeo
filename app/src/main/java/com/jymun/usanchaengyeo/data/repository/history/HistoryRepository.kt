package com.jymun.usanchaengyeo.data.repository.history

import com.jymun.usanchaengyeo.data.entity.history.HistoryEntity

interface HistoryRepository {

    suspend fun loadAllHistory(): List<HistoryEntity>

    suspend fun addHistory(historyEntity: HistoryEntity)

    suspend fun deleteHistory(historyEntity: HistoryEntity)
}