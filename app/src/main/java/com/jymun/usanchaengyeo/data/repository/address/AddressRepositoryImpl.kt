package com.jymun.usanchaengyeo.data.repository.address

import com.jymun.usanchaengyeo.data.entity.address.AddressEntity
import com.jymun.usanchaengyeo.data.entity.address.AddressName
import com.jymun.usanchaengyeo.data.source.address.AddressDataSource
import com.jymun.usanchaengyeo.util.dispatcher.DispatcherProvider
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AddressRepositoryImpl @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val addressRemoteDataSource: AddressDataSource.Remote
) : AddressRepository {

    override suspend fun coordinateToAddress(
        longitude: Double,
        latitude: Double
    ): AddressName = withContext(dispatcherProvider.io) {

        return@withContext addressRemoteDataSource.coordinateToAddressName(longitude, latitude)
    }

    override suspend fun searchAddress(
        keyword: String
    ): List<AddressEntity> = withContext(dispatcherProvider.io) {

        return@withContext addressRemoteDataSource.searchAddress(keyword).addressList
    }
}