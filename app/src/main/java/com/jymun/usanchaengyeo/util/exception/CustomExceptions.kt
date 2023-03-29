package com.jymun.usanchaengyeo.util.exception

import androidx.annotation.StringRes
import com.jymun.usanchaengyeo.R

sealed class CustomExceptions(
    @StringRes val messageResId: Int
) : Throwable() {

    object FailToConnectNetworkException : CustomExceptions(
        R.string.fail_to_connect_network_exception_message
    )

    object FailToConnectServerException : CustomExceptions(
        R.string.fail_to_connect_server_exception_message
    )

    object FailToLoadCurrentLocationException : CustomExceptions(
        R.string.fail_to_load_current_location_exception_message
    )

    object NotGrantedLocationPermissions : CustomExceptions(
        R.string.location_permission_not_granted
    )

    class UnknownException(message: String) : CustomExceptions(0)
}