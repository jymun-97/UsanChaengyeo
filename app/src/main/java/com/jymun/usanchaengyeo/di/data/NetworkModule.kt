package com.jymun.usanchaengyeo.di.data

import FlattenTypeAdapterFactory
import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jymun.usanchaengyeo.data.service.AddressService
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
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

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
    fun provideRetrofitClient(
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
    fun provideSearchRecipeService(retrofit: Retrofit): AddressService =
        retrofit.create(AddressService::class.java)
}