package com.jymun.usanchaengyeo.ui.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jymun.usanchaengyeo.data.model.weather.WeatherData
import com.jymun.usanchaengyeo.domain.weather.LoadWeatherDataUseCase
import com.jymun.usanchaengyeo.domain.weather.UpdateWeatherDataUseCase
import com.jymun.usanchaengyeo.ui.base.BaseViewModel
import com.jymun.usanchaengyeo.util.dispatcher.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    dispatcherProvider: DispatcherProvider,
    private val loadWeatherDataUseCase: LoadWeatherDataUseCase,
    private val updateWeatherDataUseCase: UpdateWeatherDataUseCase
) : BaseViewModel(dispatcherProvider) {

    private val _weatherData = MutableLiveData<WeatherData?>(WeatherData.Rain())
    val weatherData: LiveData<WeatherData?>
        get() = _weatherData

    fun loadWeatherData() = onMainDispatcher {
        _weatherData.postValue(
            loadWeatherDataUseCase()
        )
    }

    fun updateWeatherData(weatherData: WeatherData) = onMainDispatcher {
        _weatherData.postValue(weatherData)
        updateWeatherDataUseCase(weatherData)
    }

    fun updateSpeed(newSpeed: Float) = onMainDispatcher {
        val old = weatherData.value ?: return@onMainDispatcher
        val new = WeatherData.Builder()
            .weather(old.weather)
            .speed(newSpeed)
            .size(old.size)
            .amount(old.amount)
            .build()

        _weatherData.postValue(new)
        updateWeatherDataUseCase(new)
    }

    fun updateSize(newSize: Float) = onMainDispatcher {
        val old = weatherData.value ?: return@onMainDispatcher
        val new = WeatherData.Builder()
            .weather(old.weather)
            .speed(old.speed)
            .size(newSize)
            .amount(old.amount)
            .build()

        _weatherData.postValue(new)
        updateWeatherDataUseCase(new)
    }

    fun updateAmount(newAmount: Float) = onMainDispatcher {
        val old = weatherData.value ?: return@onMainDispatcher
        val new = WeatherData.Builder()
            .weather(old.weather)
            .speed(old.speed)
            .size(old.size)
            .amount(newAmount)
            .build()

        _weatherData.postValue(new)
        updateWeatherDataUseCase(new)
    }
}

