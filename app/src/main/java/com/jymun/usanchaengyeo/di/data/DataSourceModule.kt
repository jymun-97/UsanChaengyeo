package com.jymun.usanchaengyeo.di.data

import com.jymun.usanchaengyeo.data.source.address.AddressDataSource
import com.jymun.usanchaengyeo.data.source.address.AddressRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun bindsAddressRemoteDataSource(
        addressRemoteDataSource: AddressRemoteDataSource
    ): AddressDataSource.Remote
}