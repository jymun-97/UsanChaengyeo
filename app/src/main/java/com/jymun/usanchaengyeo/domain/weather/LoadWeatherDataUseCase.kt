package com.jymun.usanchaengyeo.domain.weather

import com.jymun.usanchaengyeo.data.model.weather.WeatherData
import com.jymun.usanchaengyeo.data.repository.weather.WeatherPreferencesRepository
import com.jymun.usanchaengyeo.util.dispatcher.DispatcherProvider
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoadWeatherDataUseCase @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val weatherPreferencesRepository: WeatherPreferencesRepository
) {

    suspend operator fun invoke(): WeatherData = withContext(dispatcherProvider.default) {
        val weatherPreferences = weatherPreferencesRepository.loadWeatherPreferences()
        return@withContext WeatherData.Builder()
            .weather(weatherPreferences.weather)
            .speed(weatherPreferences.speed)
            .size(weatherPreferences.size)
            .amount(weatherPreferences.amount)
            .build()
    }
}