package com.jymun.usanchaengyeo.di.data

import android.content.Context
import androidx.room.Room
import com.jymun.usanchaengyeo.data.db.HistoryDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideHistoryDatabase(
        @ApplicationContext context: Context
    ): HistoryDatabase = Room.databaseBuilder(
        context.applicationContext,
        HistoryDatabase::class.java,
        "history"
    ).build()
}