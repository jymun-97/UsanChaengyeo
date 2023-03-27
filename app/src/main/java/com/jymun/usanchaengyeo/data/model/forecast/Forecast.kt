package com.jymun.usanchaengyeo.data.model.forecast

import com.jymun.usanchaengyeo.data.model.Model
import com.jymun.usanchaengyeo.data.model.ModelType

data class Forecast(
    override val id: Long = 0L,
    override val type: ModelType = ModelType.FORECAST,
    private val forecastTime: String,
    private val forecastValue: String,
) : Model(id, type) {

    val time = "${forecastTime.substring(0, 2)}시"

    val value: Int = when (forecastValue) {
        "강수없음", "1.0mm 미만" -> 0

        "30.0~50.0mm" -> 30

        "50.0mm 이상" -> 50

        else -> forecastValue.dropLast(4).toInt()
    }

    val text: String = if (value == 0 || value > 50) value.toString() else "${value}▲"
}