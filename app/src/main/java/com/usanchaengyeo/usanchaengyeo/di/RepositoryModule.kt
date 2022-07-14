package com.usanchaengyeo.usanchaengyeo.di

import com.usanchaengyeo.usanchaengyeo.data.repository.AddressRepository
import com.usanchaengyeo.usanchaengyeo.data.repository.AddressRepositoryImpl
import com.usanchaengyeo.usanchaengyeo.data.repository.ForecastRepository
import com.usanchaengyeo.usanchaengyeo.data.repository.ForecastRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindsAddressRepository(
        addressRepositoryImpl: AddressRepositoryImpl
    ): AddressRepository

    @Singleton
    @Binds
    abstract fun bindForecastRepository(
        forecastRepositoryImpl: ForecastRepositoryImpl
    ): ForecastRepository
}