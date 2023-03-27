package com.jymun.usanchaengyeo.util.forecast

import com.jymun.usanchaengyeo.R
import com.jymun.usanchaengyeo.data.model.ModelType
import com.jymun.usanchaengyeo.data.model.forecast.Forecast
import com.jymun.usanchaengyeo.data.model.forecast.ForecastInfo
import com.jymun.usanchaengyeo.util.resources.ResourcesProvider
import javax.inject.Inject

class ForecastInfoHelper @Inject constructor(
    resourcesProvider: ResourcesProvider
) {

    private val descriptions = resourcesProvider.getStringArray(R.array.forecast_descriptions)
    private val comments = resourcesProvider.getStringArray(R.array.forecast_comments)
    private val starts = resourcesProvider.getIntegerArray(R.array.forecast_info_starts)
    private val ends = resourcesProvider.getIntegerArray(R.array.forecast_info_ends)

    private val infoList = (0 until 6).map {
        ForecastInfo(
            id = it.toLong(),
            type = ModelType.FORECAST_INFO,
            start = starts[it],
            end = ends[it],
            description = descriptions[it],
            comment = comments[it]
        )
    }

    fun get() = infoList

    fun map(forecastList: List<Forecast>): ForecastInfo {
        val max = forecastList.maxOf { it.value }
        val target = starts.findLast { it <= max } ?: 0

        return infoList[target]
    }
}
