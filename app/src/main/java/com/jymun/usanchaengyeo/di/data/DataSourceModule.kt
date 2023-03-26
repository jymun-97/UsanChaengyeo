package com.jymun.usanchaengyeo.di.data

import com.jymun.usanchaengyeo.data.source.address.AddressDataSource
import com.jymun.usanchaengyeo.data.source.address.AddressRemoteDataSource
import com.jymun.usanchaengyeo.data.source.forecast.ForecastDataSource
import com.jymun.usanchaengyeo.data.source.forecast.ForecastRemoteDataSource
import com.jymun.usanchaengyeo.data.source.history.HistoryDataSource
import com.jymun.usanchaengyeo.data.source.history.HistoryLocalDataSource
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
    abstract fun bindsAddressDataSource(
        addressRemoteDataSource: AddressRemoteDataSource
    ): AddressDataSource

    @Binds
    @Singleton
    abstract fun bindsHistoryDataSource(
        historyLocalDataSource: HistoryLocalDataSource
    ): HistoryDataSource

    @Binds
    @Singleton
    abstract fun bindsForecastDataSource(
        forecastRemoteDataSource: ForecastRemoteDataSource
    ): ForecastDataSource
}