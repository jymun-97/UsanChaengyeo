package com.jymun.usanchaengyeo.data.entity.forecast

import com.google.gson.annotations.SerializedName

data class ForecastEntity(
    val baseDate: String,
    val baseTime: String,
    val category: String,
    @SerializedName("fcstDate")
    val forecastDate: String,
    @SerializedName("fcstTime")
    val forecastTime: String,
    @SerializedName("fcstValue")
    val forecastValue: String,
    @SerializedName("nx")
    val nx: Int,
    @SerializedName("ny")
    val ny: Int
)