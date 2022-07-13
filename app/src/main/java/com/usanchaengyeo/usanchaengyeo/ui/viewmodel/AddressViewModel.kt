package com.usanchaengyeo.usanchaengyeo.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.usanchaengyeo.usanchaengyeo.data.model.Address
import com.usanchaengyeo.usanchaengyeo.data.repository.AddressRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddressViewModel @Inject constructor(
    private val addressRepository: AddressRepository
) : ViewModel() {

    val selectedAddress = MutableLiveData<Address?>(null)

    private var _addressList = MutableLiveData<List<Address>>()
    val addressList: LiveData<List<Address>>
        get() = _addressList

    val historyList: StateFlow<List<Address>> =
        addressRepository.loadHistory().stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val inputKeyword = MutableLiveData<String?>()

    fun searchAddress() = viewModelScope.launch(Dispatchers.IO) {
        inputKeyword.value?.let { keyword ->
            val response = addressRepository.searchAddress(keyword)

            if (response.isSuccessful) {
                response.body()?.let {
                    _addressList.postValue(it.addresses)
                }
            }
        }
    }

    fun addHistory(history: Address) = viewModelScope.launch(Dispatchers.IO) {
        addressRepository.addHistory(history)
    }

    fun deleteHistory(history: Address) = viewModelScope.launch(Dispatchers.IO) {
        addressRepository.deleteHistory(history)
    }
}