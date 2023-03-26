package com.jymun.usanchaengyeo.data.model.address

import com.jymun.usanchaengyeo.data.entity.address.AddressEntity
import com.jymun.usanchaengyeo.data.model.Model
import com.jymun.usanchaengyeo.data.model.ModelType

data class Address(
    override val id: Long,
    override val type: ModelType = ModelType.ADDRESS,
    private val placeName: String,
    private val addressName: String,
    private val roadAddressName: String,
    val x: String,
    val y: String
) : Model(id, type) {

    val addressText = placeName.ifEmpty { roadAddressName.ifEmpty { addressName } }
    val subAddressText = when (addressText) {
        placeName -> roadAddressName.ifEmpty { addressText }

        roadAddressName -> addressText

        else -> ""
    }

    fun toEntity() = AddressEntity(
        addressName = addressName,
        placeName = placeName,
        roadAddressName = roadAddressName,
        x = x,
        y = y
    )
}