package com.jymun.usanchaengyeo.ui.setting

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import com.github.matteobattilana.weather.PrecipType
import com.github.matteobattilana.weather.WeatherView
import com.jymun.usanchaengyeo.R
import com.jymun.usanchaengyeo.databinding.ActivitySettingBinding
import com.jymun.usanchaengyeo.ui.base.BaseActivity
import com.jymun.usanchaengyeo.util.resources.ResourcesProvider
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingActivity : BaseActivity<SettingViewModel, ActivitySettingBinding>() {

    @Inject
    lateinit var resourcesProvider: ResourcesProvider

    override val viewModel: SettingViewModel by viewModels()

    override fun setUpBinding() = binding.apply {

    }

    override fun getViewDataBinding() = ActivitySettingBinding.inflate(layoutInflater)

    override fun observeState() {}

    override fun getWeatherViewInstance(): WeatherView = binding.weatherView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.onBackPressedDispatcher.addCallback(this, backPressedCallback)

        initSpeedSlider()
        initSizeSlider()
        initAmountSlider()
        initSetWeatherSpinner()
    }

    private val backPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            finish()
            overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out)
        }
    }

    private fun initSpeedSlider() = binding.speedSlider.apply {
        addOnChangeListener { _, value, _ ->
            binding.weatherView.speed = value.toInt() * 20
        }
    }

    private fun initSizeSlider() = binding.sizeSlider.apply {
        addOnChangeListener { slider, value, _ ->
            binding.weatherView.scaleFactor = 1 + value * 0.03f
        }
    }

    private fun initAmountSlider() = binding.amountSlider.apply {
        addOnChangeListener { _, value, _ ->
            binding.weatherView.emissionRate = value
        }
    }

    private fun initSetWeatherSpinner() = binding.setWeatherSpinner.apply {
        adapter = ArrayAdapter.createFromResource(
            this@SettingActivity,
            R.array.weather_names,
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item
        )
        onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (position) {

                    0 -> binding.weatherView.precipType = PrecipType.RAIN

                    1 -> binding.weatherView.precipType = PrecipType.SNOW

                    else -> binding.weatherView.precipType = PrecipType.CLEAR
                }
            }
        }
    }
}