package com.jymun.usanchaengyeo.di.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.jymun.usanchaengyeo.WeatherPreferences
import com.jymun.usanchaengyeo.data.datastore.WeatherPreferencesSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {
    
    private val Context.weatherPreferencesDataStore: DataStore<WeatherPreferences> by dataStore(
        fileName = "WeatherPreferences.pb",
        serializer = WeatherPreferencesSerializer
    )

    @Provides
    @Singleton
    fun provideWeatherPreferencesDataStore(
        @ApplicationContext context: Context
    ) = context.weatherPreferencesDataStore
}