package com.usanchaengyeo.usanchaengyeo.data.repository

import com.usanchaengyeo.usanchaengyeo.data.model.forecast.ForecastResponse
import retrofit2.Response

interface ForecastRepository {

    suspend fun runForecast(
        date: String,
        time: String,
        x: Int,
        y: Int
    ): Response<ForecastResponse>
}