package com.jymun.harusekki.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jymun.usanchaengyeo.ui.base.LoadState
import com.jymun.usanchaengyeo.util.dispatcher.DispatcherProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

abstract class BaseViewModel(
    dispatcherProvider: DispatcherProvider
) : ViewModel(), DispatcherProvider by dispatcherProvider {

    val loadState = MutableLiveData<LoadState>()

    protected inline fun BaseViewModel.onMainDispatcher(
        crossinline body: suspend CoroutineScope.() -> Unit
    ) = viewModelScope.launch(main) {

        loadState.postValue(LoadState.Loading)
        body(this)
        loadState.postValue(LoadState.Success)
    }

    protected inline fun BaseViewModel.onIoDispatcher(
        crossinline body: suspend CoroutineScope.() -> Unit
    ) = viewModelScope.launch(io) {

        loadState.postValue(LoadState.Loading)
        body(this)
        loadState.postValue(LoadState.Success)
    }

    protected inline fun BaseViewModel.onDefaultDispatcher(
        crossinline body: suspend CoroutineScope.() -> Unit
    ) = viewModelScope.launch(default) {

        loadState.postValue(LoadState.Loading)
        body(this)
        loadState.postValue(LoadState.Success)
    }
}