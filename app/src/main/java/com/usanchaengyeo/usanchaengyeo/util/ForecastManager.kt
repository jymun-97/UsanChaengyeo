package com.usanchaengyeo.usanchaengyeo.util

import com.usanchaengyeo.usanchaengyeo.data.model.forecast.Forecast
import java.time.LocalDateTime

object ForecastManager {

    private lateinit var baseDateTime: LocalDateTime

    val date: String
        get() = with(setBaseDateTime()) {
            baseDateTime.toLocalDate().toString().filter { it != '-' }
        }

    val time: String
        get() = baseDateTime.toLocalTime().toString().substring(0, 5).filter { it != ':' }

    private fun setBaseDateTime() {
        val now = LocalDateTime.now()
        baseDateTime =
            if (now.minute < 30) now.minusMinutes(now.minute + 1L)
            else now
    }

    fun filterForecast(forecastList: List<Forecast>) = forecastList.filter { it.category == "RN1" }
}