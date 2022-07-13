package com.usanchaengyeo.usanchaengyeo.util

import android.util.Log
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.matteobattilana.weather.PrecipType
import com.github.matteobattilana.weather.WeatherView
import com.usanchaengyeo.usanchaengyeo.data.model.address.Address
import com.usanchaengyeo.usanchaengyeo.ui.adapter.AddressAdapter

object BindingAdapters {

    @BindingAdapter("app:weather")
    @JvmStatic
    fun setWeather(weatherView: WeatherView, weather: PrecipType) {
        weatherView.setWeatherData(weather)
    }

    @BindingAdapter("app:items")
    @JvmStatic
    fun setItems(recyclerView: RecyclerView, item: List<Address>) {
        (recyclerView.adapter as AddressAdapter).submitList(item.toMutableList())
    }

    @BindingAdapter("app:locationText")
    @JvmStatic
    fun setLocationText(textView: TextView, address: Address?) {
        textView.text = address?.placeName ?: "장소를 입력하세요."
        Log.d("# BindingAdapters", "$address")
    }
}