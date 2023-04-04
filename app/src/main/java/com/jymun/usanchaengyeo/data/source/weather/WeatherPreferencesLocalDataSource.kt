package com.jymun.usanchaengyeo.data.source.weather

import androidx.datastore.core.DataStore
import com.jymun.usanchaengyeo.WeatherPreferences
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherPreferencesLocalDataSource @Inject constructor(
    private val weatherPreferencesDataStore: DataStore<WeatherPreferences>
) : WeatherPreferencesDataSource {

    override suspend fun loadWeatherPreferences(): Flow<WeatherPreferences> =
        weatherPreferencesDataStore.data

    override suspend fun updateWeatherPreferences(weatherPreferences: WeatherPreferences) {
        weatherPreferencesDataStore.updateData { pref ->
            pref.toBuilder()
                .setWeather(weatherPreferences.weather)
                .setSpeed(weatherPreferences.speed)
                .setSize(weatherPreferences.size)
                .setAmount(weatherPreferences.amount)
                .build()
        }
    }
}