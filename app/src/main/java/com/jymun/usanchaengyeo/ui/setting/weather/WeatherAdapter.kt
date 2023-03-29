package com.jymun.usanchaengyeo.ui.setting.weather

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.jymun.usanchaengyeo.R
import com.jymun.usanchaengyeo.databinding.ItemWeatherBinding
import com.jymun.usanchaengyeo.util.resources.ResourcesProvider

class WeatherAdapter(
    private val resourcesProvider: ResourcesProvider,
    private val weatherList: List<WeatherData>
) : BaseAdapter() {

    override fun getCount(): Int = weatherList.size

    override fun getItem(position: Int): WeatherData = weatherList[position]

    override fun getItemId(position: Int): Long = 0L

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val weatherItem = getItem(position)

        return ItemWeatherBinding.inflate(
            LayoutInflater.from(parent?.context),
            parent,
            false
        ).apply {
            root.setBackgroundResource(R.color.transparent)
            weatherImageView.setImageDrawable(resourcesProvider.getDrawable(weatherItem.weatherImageRes))
            weatherImageView.setColorFilter(resourcesProvider.getColor(R.color.white))
            weatherTextView.visibility = View.INVISIBLE
        }.root
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val weatherItem = getItem(position)

        return ItemWeatherBinding.inflate(
            LayoutInflater.from(parent?.context),
            parent,
            false
        ).apply {
            weatherImageView.setImageDrawable(resourcesProvider.getDrawable(weatherItem.weatherImageRes))
            weatherTextView.text = resourcesProvider.getString(weatherItem.weatherTextRes)
        }.root
    }
}