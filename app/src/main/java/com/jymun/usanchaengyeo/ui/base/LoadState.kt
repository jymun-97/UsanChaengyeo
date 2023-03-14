package com.jymun.usanchaengyeo.ui.base

sealed class LoadState {

    object Success : LoadState()

    data class Error(val exception: Exception) : LoadState()

    object Loading : LoadState()

}