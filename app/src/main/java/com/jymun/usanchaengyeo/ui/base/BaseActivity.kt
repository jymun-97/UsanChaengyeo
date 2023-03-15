package com.jymun.usanchaengyeo.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import com.github.matteobattilana.weather.PrecipType
import com.github.matteobattilana.weather.WeatherView
import com.jymun.harusekki.ui.base.BaseViewModel

abstract class BaseActivity<VM : BaseViewModel, B : ViewDataBinding> : AppCompatActivity() {

    abstract val viewModel: VM

    protected lateinit var binding: B
    protected lateinit var weatherView: WeatherView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = getViewDataBinding()
        setContentView(binding.root)

        weatherView = getWeatherViewInstance()
    }

    override fun onStart() {
        super.onStart()
        weatherView.setWeatherData(PrecipType.RAIN)
    }

    override fun onStop() {
        super.onStop()
        weatherView.resetWeather()
    }

    abstract fun getViewDataBinding(): B

    abstract fun getWeatherViewInstance(): WeatherView
}