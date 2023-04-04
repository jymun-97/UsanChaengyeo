package com.jymun.usanchaengyeo.data.repository.weather

import com.jymun.usanchaengyeo.WeatherPreferences

interface WeatherPreferencesRepository {

    suspend fun loadWeatherPreferences(): WeatherPreferences

    suspend fun updateWeatherPreferences(weatherPreferences: WeatherPreferences)
}