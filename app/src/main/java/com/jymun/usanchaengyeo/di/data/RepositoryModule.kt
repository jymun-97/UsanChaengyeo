package com.jymun.usanchaengyeo.di.data

import com.jymun.usanchaengyeo.data.repository.address.AddressRepository
import com.jymun.usanchaengyeo.data.repository.address.AddressRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindsAddressRepository(
        addressRepositoryImpl: AddressRepositoryImpl
    ): AddressRepository
}