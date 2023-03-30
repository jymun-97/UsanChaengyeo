package com.jymun.usanchaengyeo.ui.setting

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import com.github.matteobattilana.weather.WeatherView
import com.jymun.usanchaengyeo.R
import com.jymun.usanchaengyeo.data.model.weather.WeatherData
import com.jymun.usanchaengyeo.databinding.ActivitySettingBinding
import com.jymun.usanchaengyeo.ui.base.BaseActivity
import com.jymun.usanchaengyeo.ui.base.LoadState
import com.jymun.usanchaengyeo.util.resources.ResourcesProvider
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingActivity : BaseActivity<SettingViewModel, ActivitySettingBinding>() {

    @Inject
    lateinit var resourcesProvider: ResourcesProvider

    private var userIsInteracting = false
    private lateinit var weatheradapter: WeatherAdapter

    override val viewModel: SettingViewModel by viewModels()

    override fun setUpBinding() = binding.apply {
        viewModel = this@SettingActivity.viewModel
        lifecycleOwner = this@SettingActivity
    }

    override fun getViewDataBinding() = ActivitySettingBinding.inflate(layoutInflater)

    override fun observeState() = viewModel.loadState.observe(this) {
        if (it is LoadState.Error) {
            Toast.makeText(
                this,
                resourcesProvider.getString(it.exception.messageResId),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun getWeatherViewInstance(): WeatherView = binding.weatherView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.onBackPressedDispatcher.addCallback(this, backPressedCallback)

        initSpeedSlider()
        initSizeSlider()
        initAmountSlider()
        initSetWeatherSpinner()

        viewModel.loadWeatherData()
        viewModel.weatherData.observe(this) {
            val data = it ?: return@observe

            Log.d("# SettingActivity", "$data")
            bindViewsWithWeatherData(data)
        }
    }

    private val backPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            finish()
            overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out)
        }
    }

    private fun initSpeedSlider() = binding.speedSlider.apply {
        addOnChangeListener { _, value, fromUser ->
            if (fromUser) viewModel.updateSpeed(value)
        }
    }

    private fun initSizeSlider() = binding.sizeSlider.apply {
        addOnChangeListener { _, value, fromUser ->
            if (fromUser) viewModel.updateSize(value)
        }
    }

    private fun initAmountSlider() = binding.amountSlider.apply {
        addOnChangeListener { _, value, fromUser ->
            if (fromUser) viewModel.updateAmount(value)
        }
    }

    private fun initSetWeatherSpinner() = binding.setWeatherSpinner.apply {
        dropDownVerticalOffset = 150
        adapter = WeatherAdapter(
            resourcesProvider,
            WeatherData.values
        ).also { weatheradapter = it }
        onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (userIsInteracting) viewModel.updateWeatherData(adapter.getItem(position) as WeatherData)
            }
        }
    }

    private fun bindViewsWithWeatherData(weatherData: WeatherData) {
        bindWeatherView(weatherData)
        bindSliders(weatherData)
        bindSpinner(weatherData)
    }

    private fun bindWeatherView(weatherData: WeatherData) = binding.weatherView.apply {
        precipType = weatherData.weather
        speed = weatherData.speed.toInt() * 20
        scaleFactor = 1 + weatherData.size * 0.03f
        emissionRate = weatherData.amount
    }

    private fun bindSliders(weatherData: WeatherData) = binding.apply {
        speedSlider.value = weatherData.speed
        sizeSlider.value = weatherData.size
        amountSlider.value = weatherData.amount
    }

    private fun bindSpinner(weatherData: WeatherData) =
        binding.setWeatherSpinner.setSelection(2 - weatherData.weather.ordinal)

    override fun onUserInteraction() {
        super.onUserInteraction()
        userIsInteracting = true
    }
}