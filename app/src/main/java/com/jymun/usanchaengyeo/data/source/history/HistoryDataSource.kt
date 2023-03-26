package com.jymun.usanchaengyeo.data.source.history

import com.jymun.usanchaengyeo.data.entity.history.HistoryEntity

interface HistoryDataSource {

    suspend fun loadAllHistory(): List<HistoryEntity>

    suspend fun addHistory(historyEntity: HistoryEntity)

    suspend fun deleteHistory(historyEntity: HistoryEntity)
}