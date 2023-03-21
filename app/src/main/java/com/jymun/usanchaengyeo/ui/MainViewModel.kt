package com.jymun.usanchaengyeo.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jymun.harusekki.ui.base.BaseViewModel
import com.jymun.usanchaengyeo.data.model.address.Address
import com.jymun.usanchaengyeo.domain.address.CoordinateToAddressUseCase
import com.jymun.usanchaengyeo.util.dispatcher.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    dispatcherProvider: DispatcherProvider,
    private val coordinateToAddressUseCase: CoordinateToAddressUseCase,
) : BaseViewModel(dispatcherProvider) {

    private val _selectedAddress = MutableLiveData<Address?>(null)
    val selectedAddress: LiveData<Address?>
        get() = _selectedAddress

    fun coordinateToAddress(
        longitude: Double,
        latitude: Double
    ) = onMainDispatcher {

        _selectedAddress.postValue(
            coordinateToAddressUseCase(longitude, latitude)
        )
    }
}