package com.usanchaengyeo.usanchaengyeo.data.model.address


import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("documents")
    val addresses: List<Address>,
    @SerializedName("meta")
    val meta: Meta
)