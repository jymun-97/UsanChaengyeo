package com.usanchaengyeo.usanchaengyeo.util

import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.core.view.updateLayoutParams
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.matteobattilana.weather.PrecipType
import com.github.matteobattilana.weather.WeatherView
import com.usanchaengyeo.usanchaengyeo.R
import com.usanchaengyeo.usanchaengyeo.data.model.address.Address
import com.usanchaengyeo.usanchaengyeo.data.model.forecast.Forecast
import com.usanchaengyeo.usanchaengyeo.ui.adapter.AddressAdapter
import com.usanchaengyeo.usanchaengyeo.ui.adapter.ForecastAdapter
import com.usanchaengyeo.usanchaengyeo.util.Const.INFO_LIST
import kotlin.math.max
import kotlin.math.min

object BindingAdapters {

    @BindingAdapter("app:weather")
    @JvmStatic
    fun setWeather(weatherView: WeatherView, weather: PrecipType) {
        weatherView.setWeatherData(weather)
    }

    @BindingAdapter("app:items")
    @JvmStatic
    fun setItems(recyclerView: RecyclerView, items: List<Any>?) {
        items ?: return

        when (recyclerView.id) {

            R.id.forecastRecyclerView ->
                (recyclerView.adapter as ForecastAdapter).submitList((items as List<Forecast>).toMutableList())

            else ->
                (recyclerView.adapter as AddressAdapter).submitList((items as List<Address>).toMutableList())

        }

    }

    @BindingAdapter("app:locationText")
    @JvmStatic
    fun setLocationText(textView: TextView, address: Address?) {
        textView.text = address?.placeName ?: "장소를 입력하세요."
    }

    @BindingAdapter("app:setBar")
    @JvmStatic
    fun setBar(view: View, strValue: String) {
        val value = if (strValue == "강수없음") 0 else strValue.dropLast(4).toInt()
        view.updateLayoutParams {
            height += min(1000, value * 30)
        }

        run {
            INFO_LIST.forEachIndexed { i, info ->
                if (info == INFO_LIST.last()) {
                    view.setBackgroundResource(R.drawable.shape_bar_blue)
                    return@run
                }
                if (value in info.start.trim().toInt()..info.end.trim().toInt()) {
                    when (i) {
                        0 -> {
                            view.setBackgroundResource(R.drawable.shape_bar_white)
                            return@run
                        }
                        1 -> {
                            view.setBackgroundResource(R.drawable.shape_bar_green)
                            return@run
                        }
                        2 -> {
                            view.setBackgroundResource(R.drawable.shape_bar_yellow)
                            return@run
                        }
                        3 -> {
                            view.setBackgroundResource(R.drawable.shape_bar_red)
                            return@run
                        }
                        4 -> {
                            view.setBackgroundResource(R.drawable.shape_bar_purple)
                            return@run
                        }
                    }
                }
            }
        }
    }

    @BindingAdapter("app:recommendText")
    @JvmStatic
    fun setRecommendText(textView: TextView, items: List<Forecast>?) {
        items ?: return

        Log.d("# BindingAdapters", "setRecommendText() called")
        var max = 0
        items.forEach { item ->
            val value = if (item.fcstValue == "강수없음") 0 else item.fcstValue.dropLast(4).toInt()
            max = max(value, max)
        }

        for (i in 0 until INFO_LIST.lastIndex) {
            val info = INFO_LIST[i]
            if (max in info.start.trim().toInt()..info.end.trim().toInt()) {
                textView.text = info.recommendText
                return
            }
        }

        textView.text = INFO_LIST.last().recommendText
    }
}