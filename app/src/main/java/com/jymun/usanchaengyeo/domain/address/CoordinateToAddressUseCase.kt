package com.jymun.usanchaengyeo.domain.address

import com.jymun.usanchaengyeo.data.model.ModelType
import com.jymun.usanchaengyeo.data.model.address.Address
import com.jymun.usanchaengyeo.data.repository.address.AddressRepository
import com.jymun.usanchaengyeo.util.dispatcher.DispatcherProvider
import com.jymun.usanchaengyeo.util.exception.CustomExceptions
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
    ): Address = withContext(dispatcherProvider.default) {

        val result = addressRepository.coordinateToAddress(longitude, latitude)
        result.addressName ?: throw CustomExceptions.FailToLoadCurrentLocationException

        val addressEntityList = addressRepository.searchAddress(result.addressName)
        if (addressEntityList.isEmpty()) {
            throw CustomExceptions.FailToLoadCurrentLocationException
        }

        val entity = addressEntityList.first()
        return@withContext Address(
            id = "${entity.placeName} ${entity.addressName}".hashCode().toLong(),
            type = ModelType.ADDRESS,
            placeName = entity.placeName,
            addressName = entity.addressName,
            roadAddressName = entity.roadAddressName,
            x = entity.x,
            y = entity.y
        )
    }
}