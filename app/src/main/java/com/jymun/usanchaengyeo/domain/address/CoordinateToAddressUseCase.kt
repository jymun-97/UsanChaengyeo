package com.jymun.usanchaengyeo.domain.address

import com.jymun.usanchaengyeo.data.entity.address.AddressName
import com.jymun.usanchaengyeo.data.repository.address.AddressRepository
import com.jymun.usanchaengyeo.util.dispatcher.DispatcherProvider
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CoordinateToAddressUseCase @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val addressRepository: AddressRepository
) {

    suspend operator fun invoke(
        longitude: Double,
        latitude: Double
    ): AddressName = withContext(dispatcherProvider.default) {

        return@withContext addressRepository.coordinateToAddress(longitude, latitude)
    }
}