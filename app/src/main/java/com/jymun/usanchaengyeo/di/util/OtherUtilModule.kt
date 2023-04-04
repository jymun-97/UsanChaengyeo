package com.jymun.usanchaengyeo.di.util

import com.jymun.usanchaengyeo.util.forecast.ForecastInfoHelper
import com.jymun.usanchaengyeo.util.resources.ResourcesProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object OtherUtilModule {

    @Provides
    @Singleton
    fun provideForecastInfoHelper(
        resourcesProvider: ResourcesProvider
    ) = ForecastInfoHelper(resourcesProvider)
}