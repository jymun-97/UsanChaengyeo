package com.jymun.usanchaengyeo.di.domain

import com.jymun.usanchaengyeo.data.repository.address.AddressRepository
import com.jymun.usanchaengyeo.domain.address.CoordinateToAddressUseCase
import com.jymun.usanchaengyeo.domain.address.SearchAddressUseCase
import com.jymun.usanchaengyeo.util.dispatcher.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object AddressDomainModule {

    @Provides
    fun provideCoordinateToAddressNameUseCase(
        dispatcherProvider: DispatcherProvider,
        addressRepository: AddressRepository
    ) = CoordinateToAddressUseCase(
        dispatcherProvider, addressRepository
    )

    @Provides
    fun provideSearchAddressUseCase(
        dispatcherProvider: DispatcherProvider,
        addressRepository: AddressRepository
    ) = SearchAddressUseCase(
        dispatcherProvider, addressRepository
    )
}