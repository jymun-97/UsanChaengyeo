package com.usanchaengyeo.usanchaengyeo.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.matteobattilana.weather.PrecipType
import com.usanchaengyeo.usanchaengyeo.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        binding.weatherView.setWeatherData(PrecipType.RAIN)
    }

    override fun onStop() {
        super.onStop()
        binding.weatherView.setWeatherData(PrecipType.CLEAR)
    }
}