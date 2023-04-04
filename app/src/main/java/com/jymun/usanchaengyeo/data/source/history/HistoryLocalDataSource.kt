package com.jymun.usanchaengyeo.data.source.history

import com.jymun.usanchaengyeo.data.db.HistoryDatabase
import com.jymun.usanchaengyeo.data.entity.history.HistoryEntity
import com.jymun.usanchaengyeo.util.dispatcher.DispatcherProvider
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HistoryLocalDataSource @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val db: HistoryDatabase
) : HistoryDataSource {

    override suspend fun loadAllHistory(): List<HistoryEntity> =
        withContext(dispatcherProvider.io) {
            return@withContext db.dao().loadAllHistory()
        }

    override suspend fun addHistory(
        historyEntity: HistoryEntity
    ) = withContext(dispatcherProvider.io) {

        db.dao().addHistory(historyEntity)
    }

    override suspend fun deleteHistory(
        historyEntity: HistoryEntity
    ) = withContext(dispatcherProvider.io) {

        db.dao().deleteHistory(historyEntity)
    }
}