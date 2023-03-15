package com.jymun.usanchaengyeo.data.repository.address

import com.jymun.usanchaengyeo.data.entity.address.AddressName

interface AddressRepository {

    suspend fun coordinateToAddress(
        longitude: Double,
        latitude: Double
    ): AddressName
}