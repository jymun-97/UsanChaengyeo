package com.jymun.usanchaengyeo.data.entity.address

import com.google.gson.annotations.SerializedName

data class AddressEntity(
    @SerializedName("place_name")
    val placeName: String,
    @SerializedName("address_name")
    val addressName: String,
    @SerializedName("road_address_name")
    val roadAddressName: String,
    val x: String,
    val y: String
)