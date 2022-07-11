package com.usanchaengyeo.usanchaengyeo.data.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "history")
data class Address(
    @SerializedName("address_name")
    val addressName: String,
    @SerializedName("place_name")
    val placeName: String,
    @SerializedName("road_address_name")
    @PrimaryKey(autoGenerate = false)
    val roadAddressName: String,
    @SerializedName("x")
    val x: String,
    @SerializedName("y")
    val y: String
)