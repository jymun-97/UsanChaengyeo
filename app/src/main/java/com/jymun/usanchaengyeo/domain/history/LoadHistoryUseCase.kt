package com.jymun.usanchaengyeo.domain.history

import com.jymun.usanchaengyeo.data.model.ModelType
import com.jymun.usanchaengyeo.data.model.address.Address
import com.jymun.usanchaengyeo.data.model.history.History
import com.jymun.usanchaengyeo.data.repository.history.HistoryRepository
import com.jymun.usanchaengyeo.util.dispatcher.DispatcherProvider
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoadHistoryUseCase @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val historyRepository: HistoryRepository
) {

    suspend operator fun invoke(): List<History> = withContext(dispatcherProvider.default) {
        return@withContext historyRepository.loadAllHistory().map { entity ->
            History(
                id = entity.dbKey.hashCode().toLong(),
                type = ModelType.HISTORY,
                address = Address(
                    id = "${entity.addressEntity.placeName} ${entity.addressEntity.addressName}".hashCode()
                        .toLong(),
                    type = ModelType.ADDRESS,
                    placeName = entity.addressEntity.placeName,
                    addressName = entity.addressEntity.addressName,
                    roadAddressName = entity.addressEntity.roadAddressName,
                    x = entity.addressEntity.x,
                    y = entity.addressEntity.y
                ),
                isPinned = entity.isPinned
            )
        }
    }
}