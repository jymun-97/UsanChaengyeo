package com.jymun.usanchaengyeo.data.source.address

import com.jymun.usanchaengyeo.data.entity.address.AddressName
import com.jymun.usanchaengyeo.data.entity.address.AddressResponse

interface AddressDataSource {

    suspend fun coordinateToAddressName(
        longitude: Double,
        latitude: Double
    ): AddressName

    suspend fun searchAddress(keyword: String): AddressResponse
}