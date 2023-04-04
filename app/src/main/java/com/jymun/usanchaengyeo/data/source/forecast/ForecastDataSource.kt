package com.jymun.usanchaengyeo.data.source.forecast

import com.jymun.usanchaengyeo.data.entity.forecast.ForecastResponse

interface ForecastDataSource {

    suspend fun runForecast(
        date: String,
        time: String,
        x: Int,
        y: Int
    ): ForecastResponse
}