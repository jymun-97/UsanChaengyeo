package com.jymun.usanchaengyeo.domain.forecast

import android.util.Log
import com.jymun.usanchaengyeo.data.model.address.Address
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
    ) = withContext(dispatcherProvider.default) {

        val (x, y) = CoordinatesConverter.convert(address.x.toDouble(), address.y.toDouble())
        val result = forecastRepository.runForecast(
            date = BaseTimeGenerator.date,
            time = BaseTimeGenerator.time,
            x = x,
            y = y
        ).filter {
            it.category == "RN1"
        }.forEach {
            Log.d("# ForecastUseCase", "$it")
        }
    }
}