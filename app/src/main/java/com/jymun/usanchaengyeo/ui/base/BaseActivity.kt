package com.jymun.usanchaengyeo.ui.base

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import com.github.matteobattilana.weather.WeatherView
import com.jymun.usanchaengyeo.data.model.weather.WeatherData

abstract class BaseActivity<VM : BaseViewModel, B : ViewDataBinding> : AppCompatActivity() {

    abstract val viewModel: VM

    protected lateinit var binding: B
    private lateinit var weatherView: WeatherView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = getViewDataBinding()
        setContentView(binding.root)

        setUpBinding()
        observeState()
        observeWeatherData()

        weatherView = getWeatherViewInstance()
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
    }

    override fun onStart() {
        super.onStart()
        loadWeatherData()
    }

    override fun onStop() {
        super.onStop()
        bindWeatherView(WeatherData.Clear)
    }

    abstract fun setUpBinding(): B

    abstract fun getViewDataBinding(): B

    abstract fun observeState()

    abstract fun getWeatherViewInstance(): WeatherView

    abstract fun loadWeatherData()

    abstract fun observeWeatherData()

    protected fun bindWeatherView(weatherData: WeatherData) = weatherView.apply {
        precipType = weatherData.weather
        speed = weatherData.speed.toInt() * 20
        scaleFactor = 1 + weatherData.size * 0.03f
        emissionRate = weatherData.amount
    }
}