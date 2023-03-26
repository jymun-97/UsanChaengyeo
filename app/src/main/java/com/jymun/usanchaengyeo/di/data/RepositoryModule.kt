package com.jymun.usanchaengyeo.di.data

import com.jymun.usanchaengyeo.data.repository.address.AddressRepository
import com.jymun.usanchaengyeo.data.repository.address.AddressRepositoryImpl
import com.jymun.usanchaengyeo.data.repository.forecast.ForecastRepository
import com.jymun.usanchaengyeo.data.repository.forecast.ForecastRepositoryImpl
import com.jymun.usanchaengyeo.data.repository.history.HistoryRepository
import com.jymun.usanchaengyeo.data.repository.history.HistoryRepositoryImpl
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

    @Binds
    @Singleton
    abstract fun bindsHistoryRepository(
        historyRepositoryImpl: HistoryRepositoryImpl
    ): HistoryRepository

    @Binds
    @Singleton
    abstract fun bindsForecastRepository(
        forecastRepositoryImpl: ForecastRepositoryImpl
    ): ForecastRepository
}