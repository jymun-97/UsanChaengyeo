package com.jymun.usanchaengyeo.domain.weather

import com.jymun.usanchaengyeo.WeatherPreferences
import com.jymun.usanchaengyeo.data.model.weather.WeatherData
import com.jymun.usanchaengyeo.data.repository.weather.WeatherPreferencesRepository
import com.jymun.usanchaengyeo.util.dispatcher.DispatcherProvider
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UpdateWeatherDataUseCase @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val weatherPreferencesRepository: WeatherPreferencesRepository
) {

    suspend operator fun invoke(
        weatherData: WeatherData
    ) = withContext(dispatcherProvider.default) {

        weatherPreferencesRepository.updateWeatherPreferences(
            WeatherPreferences.newBuilder()
                .setWeather(weatherData.weather.ordinal)
                .setSpeed(weatherData.speed)
                .setSize(weatherData.size)
                .setAmount(weatherData.amount)
                .build()
        )
    }
}