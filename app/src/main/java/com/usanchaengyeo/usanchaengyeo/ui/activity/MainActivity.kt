package com.usanchaengyeo.usanchaengyeo.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.usanchaengyeo.usanchaengyeo.databinding.ActivityMainBinding
import com.usanchaengyeo.usanchaengyeo.ui.viewmodel.ForecastViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<ForecastViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        testForecast()
        viewModel.forecastList.observe(this) {
            Log.d("# MainActivity", "$it")
        }
    }

    private fun testForecast() {
        binding.lifecycleOwner = this

        viewModel.runForecast()
    }
}