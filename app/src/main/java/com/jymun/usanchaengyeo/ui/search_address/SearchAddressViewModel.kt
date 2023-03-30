package com.jymun.usanchaengyeo.ui.search_address

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.jymun.usanchaengyeo.data.model.address.Address
import com.jymun.usanchaengyeo.data.model.weather.WeatherData
import com.jymun.usanchaengyeo.domain.address.CoordinateToAddressUseCase
import com.jymun.usanchaengyeo.domain.address.SearchAddressUseCase
import com.jymun.usanchaengyeo.domain.history.AddHistoryUseCase
import com.jymun.usanchaengyeo.domain.weather.LoadWeatherDataUseCase
import com.jymun.usanchaengyeo.ui.base.BaseViewModel
import com.jymun.usanchaengyeo.util.dispatcher.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchAddressViewModel @Inject constructor(
    dispatcherProvider: DispatcherProvider,
    private val coordinateToAddressUseCase: CoordinateToAddressUseCase,
    private val searchAddressUseCase: SearchAddressUseCase,
    private val addHistoryUseCase: AddHistoryUseCase,
    private val loadWeatherDataUseCase: LoadWeatherDataUseCase
) : BaseViewModel(dispatcherProvider) {

    private val _selectedAddress = MutableLiveData<Address?>(null)
    val selectedAddress: LiveData<Address?>
        get() = _selectedAddress

    val searchKeyword = MutableLiveData<String>()

    val searchResult = searchKeyword.switchMap { keyword ->
        val result = MutableLiveData<List<Address>?>()
        onMainDispatcher {
            result.postValue(
                if (keyword.isEmpty()) emptyList()
                else searchAddressUseCase(keyword)
            )
        }
        result
    }

    fun coordinateToAddress(
        longitude: Double,
        latitude: Double
    ) = onMainDispatcher {

        _selectedAddress.postValue(
            coordinateToAddressUseCase(longitude, latitude)
        )
    }

    fun updateSelectedAddress(address: Address) {
        _selectedAddress.postValue(address)
    }

    fun addHistory(address: Address) = onIoDispatcher {
        addHistoryUseCase(address)
    }

    private val _weatherData = MutableLiveData<WeatherData?>(WeatherData.Rain())
    val weatherData: LiveData<WeatherData?>
        get() = _weatherData

    fun loadWeatherData() = onMainDispatcher {
        _weatherData.postValue(
            loadWeatherDataUseCase()
        )
    }
}