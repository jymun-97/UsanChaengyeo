package com.jymun.usanchaengyeo.util.exception

import androidx.annotation.StringRes
import com.jymun.usanchaengyeo.R
import com.jymun.usanchaengyeo.util.resources.ResourcesProvider

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

    object FailToLoadWeatherPreferences : CustomExceptions(
        R.string.fail_to_load_weather_preferences_exception_message
    )

    class UnknownException(override val message: String?) : CustomExceptions(0)

    fun getMessage(resourcesProvider: ResourcesProvider): String {
        return if (messageResId == 0) message ?: "" else resourcesProvider.getString(messageResId)
    }
}