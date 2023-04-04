package com.jymun.usanchaengyeo.data.source.weather

import com.jymun.usanchaengyeo.WeatherPreferences
import kotlinx.coroutines.flow.Flow

interface WeatherPreferencesDataSource {

    suspend fun loadWeatherPreferences(): Flow<WeatherPreferences>

    suspend fun updateWeatherPreferences(weatherPreferences: WeatherPreferences)
}