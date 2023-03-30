package com.jymun.usanchaengyeo.di.domain

import com.jymun.usanchaengyeo.data.repository.weather.WeatherPreferencesRepository
import com.jymun.usanchaengyeo.domain.weather.LoadWeatherDataUseCase
import com.jymun.usanchaengyeo.domain.weather.UpdateWeatherDataUseCase
import com.jymun.usanchaengyeo.util.dispatcher.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object WeatherDomainModule {

    @Provides
    fun provideLoadWeatherDataUseCase(
        dispatcherProvider: DispatcherProvider,
        weatherPreferencesRepository: WeatherPreferencesRepository
    ) = LoadWeatherDataUseCase(dispatcherProvider, weatherPreferencesRepository)

    @Provides
    fun provideUpdateWeatherDataUseCase(
        dispatcherProvider: DispatcherProvider,
        weatherPreferencesRepository: WeatherPreferencesRepository
    ) = UpdateWeatherDataUseCase(dispatcherProvider, weatherPreferencesRepository)
}