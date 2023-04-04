package com.jymun.usanchaengyeo.data.repository.weather

import com.jymun.usanchaengyeo.WeatherPreferences
import com.jymun.usanchaengyeo.data.source.weather.WeatherPreferencesDataSource
import com.jymun.usanchaengyeo.util.dispatcher.DispatcherProvider
import com.jymun.usanchaengyeo.util.exception.CustomExceptions
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WeatherPreferencesRepositoryImpl @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val weatherPreferencesDataSource: WeatherPreferencesDataSource
) : WeatherPreferencesRepository {

    override suspend fun loadWeatherPreferences(): WeatherPreferences =
        withContext(dispatcherProvider.io) {
            return@withContext weatherPreferencesDataSource.loadWeatherPreferences().catch {
                throw CustomExceptions.FailToLoadWeatherPreferences
            }.first()
        }

    override suspend fun updateWeatherPreferences(
        weatherPreferences: WeatherPreferences
    ) = withContext(dispatcherProvider.io) {

        weatherPreferencesDataSource.updateWeatherPreferences(weatherPreferences)
    }
}