package com.jymun.usanchaengyeo.data.entity.history

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jymun.usanchaengyeo.data.entity.address.AddressEntity
import java.time.LocalDateTime

@Entity(tableName = "history")
data class HistoryEntity(
    val addressEntity: AddressEntity,
    val isPinned: Boolean,
    val timestamp: String = LocalDateTime.now().toString()
) {
    @PrimaryKey(autoGenerate = false)
    var dbKey = "${addressEntity.placeName} ${addressEntity.addressName}"
}