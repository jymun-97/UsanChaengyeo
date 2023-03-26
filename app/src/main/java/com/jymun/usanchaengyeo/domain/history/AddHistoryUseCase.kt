package com.jymun.usanchaengyeo.domain.history

import com.jymun.usanchaengyeo.data.entity.history.HistoryEntity
import com.jymun.usanchaengyeo.data.model.address.Address
import com.jymun.usanchaengyeo.data.model.history.History
import com.jymun.usanchaengyeo.data.repository.history.HistoryRepository
import com.jymun.usanchaengyeo.util.dispatcher.DispatcherProvider
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddHistoryUseCase @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val historyRepository: HistoryRepository
) {

    suspend operator fun invoke(address: Address) = withContext(dispatcherProvider.default) {
        historyRepository.addHistory(
            HistoryEntity(
                addressEntity = address.toEntity(),
                isPinned = false
            )
        )
    }

    suspend operator fun invoke(history: History) = withContext(dispatcherProvider.default) {
        historyRepository.addHistory(history.toEntity())
    }
}