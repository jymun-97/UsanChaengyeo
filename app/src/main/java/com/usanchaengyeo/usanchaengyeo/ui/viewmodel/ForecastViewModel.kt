package com.usanchaengyeo.usanchaengyeo.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.usanchaengyeo.usanchaengyeo.data.model.address.Address
import com.usanchaengyeo.usanchaengyeo.data.model.forecast.Forecast
import com.usanchaengyeo.usanchaengyeo.data.repository.ForecastRepository
import com.usanchaengyeo.usanchaengyeo.util.CoordinatesConverter
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

    fun runForecast(selectedAddress: Address) = viewModelScope.launch(Dispatchers.IO) {
        val coordinate = CoordinatesConverter.convert(
            selectedAddress.x.toDouble(),
            selectedAddress.y.toDouble()
        )
        val response = forecastRepository.runForecast(
            ForecastManager.date,
            ForecastManager.time,
            coordinate.first,
            coordinate.second
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