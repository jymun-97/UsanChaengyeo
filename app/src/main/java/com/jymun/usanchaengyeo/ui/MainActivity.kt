package com.jymun.usanchaengyeo.ui

import android.os.Bundle
import androidx.activity.viewModels
import com.jymun.usanchaengyeo.databinding.ActivityMainBinding
import com.jymun.usanchaengyeo.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

    override val viewModel: MainViewModel by viewModels()

    override fun getViewDataBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun getWeatherViewInstance() = binding.weatherView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
}