package com.usanchaengyeo.usanchaengyeo.data.model


import com.google.gson.annotations.SerializedName

data class Address(
    @SerializedName("address_name")
    val addressName: String,
    @SerializedName("place_name")
    val placeName: String,
    @SerializedName("road_address_name")
    val roadAddressName: String,
    @SerializedName("x")
    val x: String,
    @SerializedName("y")
    val y: String
)