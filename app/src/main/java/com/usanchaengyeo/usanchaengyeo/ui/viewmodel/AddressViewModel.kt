package com.usanchaengyeo.usanchaengyeo.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AddressViewModel : ViewModel() {

    private val _address = MutableLiveData<String>(INIT_ADDRESS)
    val address: LiveData<String>
        get() = _address


    companion object {
        const val INIT_ADDRESS = "위치를 입력하세요."
    }
}