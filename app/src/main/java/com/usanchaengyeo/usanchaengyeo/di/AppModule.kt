package com.usanchaengyeo.usanchaengyeo.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.usanchaengyeo.usanchaengyeo.data.db.SearchHistoryDatabase
import com.usanchaengyeo.usanchaengyeo.data.service.AddressSearchService
import com.usanchaengyeo.usanchaengyeo.data.service.ForecastService
import com.usanchaengyeo.usanchaengyeo.util.Const.FORECAST_BASE_URL
import com.usanchaengyeo.usanchaengyeo.util.Const.KAKAO_BASE_URL
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
object AppModule {

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class AddressRetrofit

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class ForecastRetrofit

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideGson() = GsonBuilder().setLenient().create()

    @Singleton
    @AddressRetrofit
    @Provides
    fun provideKakaoRetrofitClient(okHttpClient: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(KAKAO_BASE_URL)
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @AddressRetrofit
    @Provides
    fun provideAddressSearchService(
        @AddressRetrofit retrofit: Retrofit
    ): AddressSearchService {
        return retrofit.create(AddressSearchService::class.java)
    }

    @Singleton
    @ForecastRetrofit
    @Provides
    fun provideForecastRetrofitClient(okHttpClient: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(FORECAST_BASE_URL)
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @ForecastRetrofit
    @Provides
    fun provideForecastService(
        @ForecastRetrofit retrofit: Retrofit
    ): ForecastService {
        return retrofit.create(ForecastService::class.java)
    }

    @Singleton
    @Provides
    fun provideSearchHistoryDatabase(
        @ApplicationContext context: Context
    ): SearchHistoryDatabase {

        return Room.databaseBuilder(
            context.applicationContext,
            SearchHistoryDatabase::class.java,
            "serach-history"
        ).build()
    }
}