package com.jymun.usanchaengyeo.data.repository.forecast

import com.jymun.usanchaengyeo.data.entity.forecast.ForecastEntity

interface ForecastRepository {

    suspend fun runForecast(
        date: String,
        time: String,
        x: Int,
        y: Int
    ): List<ForecastEntity>
}