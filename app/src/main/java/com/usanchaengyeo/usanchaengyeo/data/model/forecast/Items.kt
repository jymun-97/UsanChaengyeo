package com.usanchaengyeo.usanchaengyeo.data.model.forecast


import com.google.gson.annotations.SerializedName

data class Items(
    @SerializedName("item")
    val forecast: List<Forecast>
)