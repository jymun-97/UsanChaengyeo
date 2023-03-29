package com.jymun.usanchaengyeo.ui.setting.weather

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.github.matteobattilana.weather.PrecipType
import com.jymun.usanchaengyeo.R

sealed class WeatherData(
    val weather: PrecipType,
    val defaultSpeed: Float,
    val defaultSize: Float,
    val defaultAmount: Float,
    @StringRes val weatherTextRes: Int,
    @DrawableRes val weatherImageRes: Int
) {
    data class Rain(
        val speed: Float = 40f,
        val size: Float = 25f,
        val amount: Float = 70f
    ) : WeatherData(
        PrecipType.RAIN,
        40f,
        25f,
        70f,
        R.string.weather_rain,
        R.drawable.ic_weather_rain
    )

    data class Snow(
        val speed: Float = 20f,
        val size: Float = 10f,
        val amount: Float = 30f
    ) : WeatherData(
        PrecipType.SNOW,
        20f,
        10f,
        30f,
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

    companion object {
        val values = listOf(
            Rain(),
            Snow(),
            Clear
        )
    }
}