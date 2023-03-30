package com.jymun.usanchaengyeo.data.datastore

import androidx.datastore.core.Serializer
import com.jymun.usanchaengyeo.WeatherPreferences
import com.jymun.usanchaengyeo.data.model.weather.WeatherData
import com.jymun.usanchaengyeo.util.exception.CustomExceptions
import java.io.InputStream
import java.io.OutputStream

object WeatherPreferencesSerializer : Serializer<WeatherPreferences> {

    private val defaultWeatherData = WeatherData.Rain()

    override val defaultValue: WeatherPreferences
        get() = WeatherPreferences.newBuilder()
            .setWeather(defaultWeatherData.weather.ordinal)
            .setSpeed(defaultWeatherData.speed)
            .setSize(defaultWeatherData.size)
            .setAmount(defaultWeatherData.amount)
            .build()

    override suspend fun readFrom(input: InputStream): WeatherPreferences {
        try {
            return WeatherPreferences.parseFrom(input)
        } catch (exception: Throwable) {
            throw CustomExceptions.FailToLoadWeatherPreferences
        }
    }

    override suspend fun writeTo(t: WeatherPreferences, output: OutputStream) {
        t.writeTo(output)
    }
}