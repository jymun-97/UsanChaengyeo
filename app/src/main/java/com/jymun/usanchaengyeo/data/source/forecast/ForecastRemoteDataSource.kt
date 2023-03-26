package com.jymun.usanchaengyeo.data.source.forecast

import com.jymun.usanchaengyeo.data.entity.forecast.ForecastResponse
import com.jymun.usanchaengyeo.data.service.ForecastService
import com.jymun.usanchaengyeo.di.data.NetworkModule
import com.jymun.usanchaengyeo.util.dispatcher.DispatcherProvider
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ForecastRemoteDataSource @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    @NetworkModule.ForecastServiceClient
    private val forecastService: ForecastService
) : ForecastDataSource {

    override suspend fun runForecast(
        date: String,
        time: String,
        x: Int,
        y: Int
    ): ForecastResponse = withContext(dispatcherProvider.io) {

        val response = forecastService.runForecast(
            date = date,
            time = time,
            x = x,
            y = y
        )
        return@withContext response.body()!!
    }
}