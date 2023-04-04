package com.jymun.usanchaengyeo.domain.forecast

import com.jymun.usanchaengyeo.data.model.ModelType
import com.jymun.usanchaengyeo.data.model.address.Address
import com.jymun.usanchaengyeo.data.model.forecast.Forecast
import com.jymun.usanchaengyeo.data.repository.forecast.ForecastRepository
import com.jymun.usanchaengyeo.util.dispatcher.DispatcherProvider
import com.jymun.usanchaengyeo.util.forecast.BaseTimeGenerator
import com.jymun.usanchaengyeo.util.forecast.CoordinatesConverter
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ForecastUseCase @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val forecastRepository: ForecastRepository
) {

    suspend operator fun invoke(
        address: Address
    ): List<Forecast> = withContext(dispatcherProvider.default) {

        val (x, y) = CoordinatesConverter.convert(address.x.toDouble(), address.y.toDouble())
        return@withContext forecastRepository.runForecast(
            date = BaseTimeGenerator.date,
            time = BaseTimeGenerator.time,
            x = x,
            y = y
        ).filter {
            it.category == "RN1"
        }.map { forecastEntity ->
            Forecast(
                id = forecastEntity.forecastTime.hashCode().toLong(),
                type = ModelType.FORECAST,
                forecastTime = forecastEntity.forecastTime,
                forecastValue = forecastEntity.forecastValue
            )
        }
    }
}