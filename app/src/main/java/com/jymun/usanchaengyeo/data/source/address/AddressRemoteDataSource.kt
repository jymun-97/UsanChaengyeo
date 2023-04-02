package com.jymun.usanchaengyeo.data.source.address

import com.jymun.usanchaengyeo.data.entity.address.AddressName
import com.jymun.usanchaengyeo.data.entity.address.AddressResponse
import com.jymun.usanchaengyeo.data.service.AddressService
import com.jymun.usanchaengyeo.di.data.NetworkModule
import com.jymun.usanchaengyeo.util.dispatcher.DispatcherProvider
import com.jymun.usanchaengyeo.util.exception.CustomExceptions
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AddressRemoteDataSource @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    @NetworkModule.AddressServiceClient
    private val addressServiceClient: AddressService
) : AddressDataSource {

    override suspend fun coordinateToAddressName(
        longitude: Double,
        latitude: Double
    ): AddressName = withContext(dispatcherProvider.io) {

        val response = addressServiceClient.coordinateToAddressName(longitude, latitude)

        if (!response.isSuccessful || response.body() == null) throw CustomExceptions.FailToLoadCurrentLocationException
        return@withContext response.body()!!
    }

    override suspend fun searchAddress(
        keyword: String
    ): AddressResponse = withContext(dispatcherProvider.io) {

        val response = addressServiceClient.searchAddressByKeyword(keyword)

        if (!response.isSuccessful || response.body() == null) throw CustomExceptions.FailToConnectServerException
        return@withContext response.body()!!
    }
}