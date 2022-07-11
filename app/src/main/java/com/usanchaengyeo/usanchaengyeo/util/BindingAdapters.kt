package com.usanchaengyeo.usanchaengyeo.util

import androidx.databinding.BindingAdapter
import com.github.matteobattilana.weather.PrecipType
import com.github.matteobattilana.weather.WeatherView

object BindingAdapters {

    @BindingAdapter("app:weather")
    @JvmStatic
    fun setWeather(weatherView: WeatherView, weather: PrecipType) {
        weatherView.setWeatherData(weather)
    }
}