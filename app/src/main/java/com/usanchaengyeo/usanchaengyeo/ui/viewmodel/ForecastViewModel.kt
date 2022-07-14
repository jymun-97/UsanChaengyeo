package com.usanchaengyeo.usanchaengyeo.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.usanchaengyeo.usanchaengyeo.data.model.forecast.Forecast
import com.usanchaengyeo.usanchaengyeo.data.repository.ForecastRepository
import com.usanchaengyeo.usanchaengyeo.util.ForecastManager
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
        Log.d("# ForecastViewModel", "${ForecastManager.date} ${ForecastManager.time}")
        val response = forecastRepository.runForecast(
            ForecastManager.date,
            ForecastManager.time,
            66,
            100
        )
        if (response.isSuccessful) {
            response.body()?.let {
                _forecastList.postValue(
                    ForecastManager.filterForecast(it.response.body.items.forecast)
                )
            }
        }
    }
}