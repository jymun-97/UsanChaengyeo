package com.jymun.usanchaengyeo.di.domain

import com.jymun.usanchaengyeo.data.repository.history.HistoryRepository
import com.jymun.usanchaengyeo.domain.history.AddHistoryUseCase
import com.jymun.usanchaengyeo.domain.history.DeleteHistoryUseCase
import com.jymun.usanchaengyeo.domain.history.LoadHistoryUseCase
import com.jymun.usanchaengyeo.util.dispatcher.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object HistoryDomainModule {

    @Provides
    fun provideLoadHistoryUseCase(
        dispatcherProvider: DispatcherProvider,
        historyRepository: HistoryRepository
    ) = LoadHistoryUseCase(dispatcherProvider, historyRepository)

    @Provides
    fun provideAddHistoryUseCase(
        dispatcherProvider: DispatcherProvider,
        historyRepository: HistoryRepository
    ) = AddHistoryUseCase(dispatcherProvider, historyRepository)

    @Provides
    fun provideDeleteHistoryUseCase(
        dispatcherProvider: DispatcherProvider,
        historyRepository: HistoryRepository
    ) = DeleteHistoryUseCase(dispatcherProvider, historyRepository)
}