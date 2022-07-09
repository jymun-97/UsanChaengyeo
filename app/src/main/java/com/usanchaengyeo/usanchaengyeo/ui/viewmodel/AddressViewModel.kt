package com.usanchaengyeo.usanchaengyeo.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.usanchaengyeo.usanchaengyeo.data.model.SearchResponse
import com.usanchaengyeo.usanchaengyeo.data.repository.AddressSearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddressViewModel @Inject constructor(
    private val addressSearchRepository: AddressSearchRepository
) : ViewModel() {

    private val _address = MutableLiveData<String>(INIT_ADDRESS)
    val address: LiveData<String>
        get() = _address

    private val _addressSearchResult = MutableLiveData<SearchResponse>()
    val addressSearchResult: LiveData<SearchResponse>
        get() = _addressSearchResult

    val inputKeyword = MutableLiveData<String?>()

    fun searchAddress() = viewModelScope.launch(Dispatchers.IO) {
        inputKeyword.value?.let { keyword ->
            val response = addressSearchRepository.searchAddress(keyword)

            if (response.isSuccessful) {
                response.body()?.let {
                    _addressSearchResult.postValue(it)
                }
            }
        }
    }

    companion object {
        const val INIT_ADDRESS = "위치를 입력하세요."
    }
}