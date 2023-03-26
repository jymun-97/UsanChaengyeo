package com.jymun.usanchaengyeo.ui.forecast

import com.jymun.harusekki.ui.base.BaseViewModel
import com.jymun.usanchaengyeo.data.model.address.Address
import com.jymun.usanchaengyeo.domain.forecast.ForecastUseCase
import com.jymun.usanchaengyeo.util.dispatcher.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ForecastViewModel @Inject constructor(
    dispatcherProvider: DispatcherProvider,
    private val forecastUseCase: ForecastUseCase
) : BaseViewModel(dispatcherProvider) {

    fun runForecast(address: Address?) = onMainDispatcher {
        address ?: return@onMainDispatcher
        forecastUseCase(address)
    }
}