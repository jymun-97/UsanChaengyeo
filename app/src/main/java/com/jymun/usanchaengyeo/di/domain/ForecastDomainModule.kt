package com.jymun.usanchaengyeo.di.domain

import com.jymun.usanchaengyeo.data.repository.forecast.ForecastRepository
import com.jymun.usanchaengyeo.domain.forecast.ForecastUseCase
import com.jymun.usanchaengyeo.util.dispatcher.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object ForecastDomainModule {

    @Provides
    fun provideForecastUseCase(
        dispatcherProvider: DispatcherProvider,
        forecastRepository: ForecastRepository
    ) = ForecastUseCase(
        dispatcherProvider, forecastRepository
    )
}