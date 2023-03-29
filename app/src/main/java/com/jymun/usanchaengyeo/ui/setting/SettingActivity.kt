package com.jymun.usanchaengyeo.ui.setting

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import com.github.matteobattilana.weather.WeatherView
import com.jymun.usanchaengyeo.R
import com.jymun.usanchaengyeo.databinding.ActivitySettingBinding
import com.jymun.usanchaengyeo.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingActivity : BaseActivity<SettingViewModel, ActivitySettingBinding>() {

    override val viewModel: SettingViewModel by viewModels()

    override fun setUpBinding() = binding.apply {

    }

    override fun getViewDataBinding() = ActivitySettingBinding.inflate(layoutInflater)

    override fun observeState() {}

    override fun getWeatherViewInstance(): WeatherView = binding.weatherView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.onBackPressedDispatcher.addCallback(this, backPressedCallback)
    }

    private val backPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            finish()
            overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out)
        }
    }
}