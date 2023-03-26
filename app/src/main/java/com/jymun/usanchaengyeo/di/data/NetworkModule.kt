package com.jymun.usanchaengyeo.di.data

import FlattenTypeAdapterFactory
import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jymun.usanchaengyeo.data.service.AddressService
import com.jymun.usanchaengyeo.data.service.ForecastService
import com.jymun.usanchaengyeo.data.service.NetworkConstant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class AddressServiceClient

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class ForecastServiceClient

    @Provides
    @Singleton
    fun provideOkHttpClient(
        @ApplicationContext context: Context
    ): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

        return OkHttpClient.Builder()
            .addNetworkInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder()
        .registerTypeAdapterFactory(FlattenTypeAdapterFactory())
        .create()

    @Provides
    @Singleton
    @AddressServiceClient
    fun provideAddressRetrofitClient(
        okHttpClient: OkHttpClient,
        gson: Gson
    ): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(NetworkConstant.ADDRESS_BASE_URL)
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    @AddressServiceClient
    fun provideAddressService(
        @AddressServiceClient
        retrofit: Retrofit
    ): AddressService =
        retrofit.create(AddressService::class.java)

    @Provides
    @Singleton
    @ForecastServiceClient
    fun provideForecastRetrofitClient(
        okHttpClient: OkHttpClient,
        gson: Gson
    ): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(NetworkConstant.FORECAST_BASE_URL)
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    @ForecastServiceClient
    fun provideForecastService(
        @ForecastServiceClient
        retrofit: Retrofit
    ): ForecastService =
        retrofit.create(ForecastService::class.java)
}