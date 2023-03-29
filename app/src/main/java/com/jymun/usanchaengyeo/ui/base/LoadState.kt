package com.jymun.usanchaengyeo.ui.base

import com.jymun.usanchaengyeo.util.exception.CustomExceptions

sealed class LoadState {

    object Success : LoadState()

    data class Error(val exception: CustomExceptions) : LoadState()

    object Loading : LoadState()

}