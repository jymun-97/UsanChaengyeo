package com.usanchaengyeo.usanchaengyeo.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.usanchaengyeo.usanchaengyeo.data.model.forecast.Forecast
import com.usanchaengyeo.usanchaengyeo.data.repository.ForecastRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForecastViewModel @Inject constructor(
    private val forecastRepository: ForecastRepository
) : ViewModel() {

    private val _forecastList = MutableLiveData<List<Forecast>>()
    val forecastList: LiveData<List<Forecast>>
        get() = _forecastList

    fun runForecast() = viewModelScope.launch(Dispatchers.IO) {
        val response = forecastRepository.runForecast(
            "220713",
            "1730",
            66,
            100
        )
        if (response.isSuccessful) {
            response.body()?.let {
                _forecastList.postValue(
                    it.response.body.items.forecast
                )
            }
        }
    }
}