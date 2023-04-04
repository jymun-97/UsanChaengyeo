package com.jymun.usanchaengyeo.data.model.forecast

import com.jymun.usanchaengyeo.data.model.Model
import com.jymun.usanchaengyeo.data.model.ModelType

data class ForecastInfo(
    override val id: Long,
    override val type: ModelType = ModelType.FORECAST_INFO,
    val start: Int,
    val end: Int,
    val description: String,
    val comment: String
) : Model(id, type) {

    val text: String = "$start ~ ${if (end == -1) "  " else end} : $description"
}