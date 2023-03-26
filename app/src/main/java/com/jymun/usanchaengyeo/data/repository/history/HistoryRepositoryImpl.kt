package com.jymun.usanchaengyeo.data.repository.history

import com.jymun.usanchaengyeo.data.entity.history.HistoryEntity
import com.jymun.usanchaengyeo.data.source.history.HistoryDataSource
import com.jymun.usanchaengyeo.util.dispatcher.DispatcherProvider
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HistoryRepositoryImpl @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val historyDataSource: HistoryDataSource
) : HistoryRepository {

    override suspend fun loadAllHistory(): List<HistoryEntity> =
        withContext(dispatcherProvider.io) {
            return@withContext historyDataSource.loadAllHistory()
        }

    override suspend fun addHistory(
        historyEntity: HistoryEntity
    ) = withContext(dispatcherProvider.io) {

        historyDataSource.addHistory(historyEntity)
    }

    override suspend fun deleteHistory(
        historyEntity: HistoryEntity
    ) = withContext(dispatcherProvider.io) {

        historyDataSource.deleteHistory(historyEntity)
    }
}