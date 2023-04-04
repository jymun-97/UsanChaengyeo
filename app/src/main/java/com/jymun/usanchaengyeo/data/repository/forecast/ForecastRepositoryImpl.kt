package com.jymun.usanchaengyeo.data.repository.forecast

import com.jymun.usanchaengyeo.data.entity.forecast.ForecastEntity
import com.jymun.usanchaengyeo.data.source.forecast.ForecastDataSource
import com.jymun.usanchaengyeo.util.dispatcher.DispatcherProvider
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ForecastRepositoryImpl @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val forecastDataSource: ForecastDataSource
) : ForecastRepository {

    override suspend fun runForecast(
        date: String,
        time: String,
        x: Int,
        y: Int
    ): List<ForecastEntity> = withContext(dispatcherProvider.io) {

        return@withContext forecastDataSource.runForecast(date, time, x, y).result
    }
}