package com.usanchaengyeo.usanchaengyeo.data.repository

import com.usanchaengyeo.usanchaengyeo.data.model.forecast.ForecastResponse
import com.usanchaengyeo.usanchaengyeo.data.service.ForecastService
import com.usanchaengyeo.usanchaengyeo.di.AppModule
import retrofit2.Response
import javax.inject.Inject

class ForecastRepositoryImpl @Inject constructor(
    @AppModule.ForecastRetrofit
    private val forecastService: ForecastService
) : ForecastRepository {

    override suspend fun runForecast(
        date: String,
        time: String,
        x: Int,
        y: Int
    ): Response<ForecastResponse> =
        forecastService.runForecast(
            date = date,
            time = time,
            x = x,
            y = y
        )
}