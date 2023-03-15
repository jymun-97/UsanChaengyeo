package com.jymun.usanchaengyeo.data.source.address

import com.jymun.usanchaengyeo.data.entity.address.AddressName

interface AddressDataSource {

    interface Remote {
        suspend fun coordinateToAddressName(
            longitude: Double,
            latitude: Double
        ): AddressName
    }
}