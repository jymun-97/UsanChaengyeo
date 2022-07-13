package com.usanchaengyeo.usanchaengyeo.data.model.forecast


import com.google.gson.annotations.SerializedName

data class Response(
    @SerializedName("body")
    val body: Body,
    @SerializedName("header")
    val header: Header
)