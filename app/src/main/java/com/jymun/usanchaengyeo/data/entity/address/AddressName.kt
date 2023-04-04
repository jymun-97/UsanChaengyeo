package com.jymun.usanchaengyeo.data.entity.address

import Flatten

data class AddressName(
    @Flatten("documents.0.address.address_name")
    val addressName: String?,
    @Flatten("documents.0.road_address.address_name")
    val roadAddressName: String?
)