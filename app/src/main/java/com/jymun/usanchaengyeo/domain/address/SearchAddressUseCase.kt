package com.jymun.usanchaengyeo.domain.address

import com.jymun.usanchaengyeo.data.model.ModelType
import com.jymun.usanchaengyeo.data.model.address.Address
import com.jymun.usanchaengyeo.data.repository.address.AddressRepository
import com.jymun.usanchaengyeo.util.dispatcher.DispatcherProvider
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchAddressUseCase @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val addressRepository: AddressRepository
) {

    suspend operator fun invoke(
        keyword: String
    ): List<Address> = withContext(dispatcherProvider.default) {

        return@withContext addressRepository.searchAddress(keyword).map {
            Address(
                id = "${it.placeName} ${it.addressName}".hashCode().toLong(),
                type = ModelType.ADDRESS,
                placeName = it.placeName,
                addressName = it.addressName,
                roadAddressName = it.roadAddressName,
                x = it.x,
                y = it.y
            )
        }
    }
}