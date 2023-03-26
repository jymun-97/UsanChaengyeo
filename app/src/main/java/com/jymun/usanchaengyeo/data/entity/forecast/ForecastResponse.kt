package com.jymun.usanchaengyeo.data.entity.forecast

import Flatten

data class ForecastResponse(
    @Flatten("response.header.resultCode")
    val resultCode: String,
    @Flatten("response.body.items.item")
    val result: List<ForecastEntity>
)