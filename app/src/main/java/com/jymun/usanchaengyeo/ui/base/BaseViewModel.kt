package com.jymun.usanchaengyeo.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jymun.usanchaengyeo.util.dispatcher.DispatcherProvider
import com.jymun.usanchaengyeo.util.exception.CustomExceptions
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.net.ConnectException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

abstract class BaseViewModel(
    dispatcherProvider: DispatcherProvider
) : ViewModel(), DispatcherProvider by dispatcherProvider {

    val loadState = MutableLiveData<LoadState>(LoadState.Loading)

    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        loadState.postValue(
            when (throwable) {
                is CustomExceptions -> LoadState.Error(throwable)

                is UnknownHostException, is ConnectException -> LoadState.Error(CustomExceptions.FailToConnectNetworkException)

                is SocketException, is SocketTimeoutException -> LoadState.Error(CustomExceptions.FailToConnectServerException)

                else -> LoadState.Error(CustomExceptions.UnknownException(throwable.message ?: ""))
            }
        )
    }

    fun exceptionCaused(exception: CustomExceptions) =
        loadState.postValue(LoadState.Error(exception))

    protected inline fun BaseViewModel.onMainDispatcher(
        crossinline body: suspend CoroutineScope.() -> Unit
    ) = viewModelScope.launch(main + exceptionHandler) {

        loadState.postValue(LoadState.Loading)
        body(this)
        loadState.postValue(LoadState.Success)
    }

    protected inline fun BaseViewModel.onIoDispatcher(
        crossinline body: suspend CoroutineScope.() -> Unit
    ) = viewModelScope.launch(io + exceptionHandler) {

        loadState.postValue(LoadState.Loading)
        body(this)
        loadState.postValue(LoadState.Success)
    }

    protected inline fun BaseViewModel.onDefaultDispatcher(
        crossinline body: suspend CoroutineScope.() -> Unit
    ) = viewModelScope.launch(default + exceptionHandler) {

        loadState.postValue(LoadState.Loading)
        body(this)
        loadState.postValue(LoadState.Success)
    }
}