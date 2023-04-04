package com.jymun.usanchaengyeo.data.entity.address

import com.google.gson.annotations.SerializedName

data class AddressResponse(
    @SerializedName("documents")
    val addressList: List<AddressEntity>
)