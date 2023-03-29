package com.jymun.usanchaengyeo.ui.forecast

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jymun.usanchaengyeo.data.model.address.Address
import com.jymun.usanchaengyeo.data.model.forecast.Forecast
import com.jymun.usanchaengyeo.domain.forecast.ForecastUseCase
import com.jymun.usanchaengyeo.ui.base.BaseViewModel
import com.jymun.usanchaengyeo.util.dispatcher.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ForecastViewModel @Inject constructor(
    dispatcherProvider: DispatcherProvider,
    private val forecastUseCase: ForecastUseCase
) : BaseViewModel(dispatcherProvider) {

    private val selectedAddress = MutableLiveData<Address>()

    private val _forecastResult = MutableLiveData<List<Forecast>?>()
    val forecastResult: LiveData<List<Forecast>?>
        get() = _forecastResult

    fun runForecast(address: Address) = onMainDispatcher {
        selectedAddress.postValue(address)
        _forecastResult.postValue(
            forecastUseCase(address)
        )
    }

    fun runForecast(onSelectedAddressNotExisted: () -> Unit) = onMainDispatcher {
        selectedAddress.value?.let {
            _forecastResult.postValue(
                forecastUseCase(it)
            )
        } ?: run {
            onSelectedAddressNotExisted()
        }
    }
}