package com.jymun.usanchaengyeo.data.model.weather

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.github.matteobattilana.weather.PrecipType
import com.jymun.usanchaengyeo.R

sealed class WeatherData(
    val weather: PrecipType,
    open val speed: Float,
    open val size: Float,
    open val amount: Float,
    @StringRes val weatherTextRes: Int,
    @DrawableRes val weatherImageRes: Int
) {
    data class Rain(
        override val speed: Float = 40f,
        override val size: Float = 25f,
        override val amount: Float = 70f
    ) : WeatherData(
        PrecipType.RAIN,
        speed,
        size,
        amount,
        R.string.weather_rain,
        R.drawable.ic_weather_rain
    )

    data class Snow(
        override val speed: Float = 20f,
        override val size: Float = 10f,
        override val amount: Float = 30f
    ) : WeatherData(
        PrecipType.SNOW,
        speed,
        size,
        amount,
        R.string.weather_snow,
        R.drawable.ic_weather_snow
    )

    object Clear : WeatherData(
        PrecipType.CLEAR,
        0f,
        0f,
        0f,
        R.string.weather_clear,
        R.drawable.ic_weather_clear
    )

    class Builder {
        private var weather: PrecipType = PrecipType.RAIN
        private var speed: Float = 0f
        private var size: Float = 0f
        private var amount: Float = 0f

        fun weather(value: PrecipType) = apply { weather = value }

        fun weather(value: Int) = apply { weather = PrecipType.values()[value] }

        fun speed(value: Float) = apply { speed = value }

        fun size(value: Float) = apply { size = value }

        fun amount(value: Float) = apply { amount = value }

        fun build() = when (weather) {
            PrecipType.RAIN -> Rain(speed, size, amount)

            PrecipType.SNOW -> Snow(speed, size, amount)

            else -> Clear
        }
    }

    companion object {
        val values = listOf(
            Rain(),
            Snow(),
            Clear
        )

        fun defaultOf(weather: PrecipType) = when (weather) {
            PrecipType.RAIN -> Rain()

            PrecipType.SNOW -> Snow()

            else -> Clear
        }
    }
}