package com.usanchaengyeo.usanchaengyeo.di

import android.content.Context
import androidx.room.Room
import com.usanchaengyeo.usanchaengyeo.data.db.SearchHistoryDatabase
import com.usanchaengyeo.usanchaengyeo.data.service.AddressSearchService
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
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

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
    fun provideKakaoRetrofitClient(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(KAKAO_BASE_URL)
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideAddressSearchService(retrofit: Retrofit): AddressSearchService {
        return retrofit.create(AddressSearchService::class.java)
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