package com.jymun.usanchaengyeo.data.model.history

import com.jymun.usanchaengyeo.data.entity.history.HistoryEntity
import com.jymun.usanchaengyeo.data.model.Model
import com.jymun.usanchaengyeo.data.model.ModelType
import com.jymun.usanchaengyeo.data.model.address.Address

data class History(
    override val id: Long,
    override val type: ModelType = ModelType.HISTORY,
    val address: Address,
    val isPinned: Boolean
) : Model(id, type) {

    fun toEntity() = HistoryEntity(
        addressEntity = address.toEntity(),
        isPinned = isPinned
    )
}