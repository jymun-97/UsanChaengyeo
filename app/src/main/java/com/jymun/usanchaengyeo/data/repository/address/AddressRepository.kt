package com.jymun.usanchaengyeo.data.repository.address

import com.jymun.usanchaengyeo.data.entity.address.AddressEntity
import com.jymun.usanchaengyeo.data.entity.address.AddressName

interface AddressRepository {

    suspend fun coordinateToAddress(
        longitude: Double,
        latitude: Double
    ): AddressName

    suspend fun searchAddress(keyword: String): List<AddressEntity>
}