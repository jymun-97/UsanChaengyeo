package com.jymun.usanchaengyeo.di.util

import com.jymun.usanchaengyeo.util.resources.ResourcesProvider
import com.jymun.usanchaengyeo.util.resources.ResourcesProviderImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ResourcesModule {

    @Binds
    abstract fun bindResourcesProvider(provider: ResourcesProviderImpl): ResourcesProvider
}