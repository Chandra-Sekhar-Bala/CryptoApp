package com.cryptoapp.demo.di

import android.app.Application
import android.content.Context
import com.cryptoapp.demo.R
import com.cryptoapp.demo.helper.CONSTANTS
import com.cryptoapp.demo.repository.network.CryptoApi
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.InputStream
import java.util.Properties
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object HiltModules {

    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun provideProperties(context: Context): Properties {
        val properties = Properties()
        val inputStream: InputStream = context.resources.openRawResource(R.raw.config)
        properties.load(inputStream)
        return properties
    }

    @Named(CONSTANTS.BASE_URL)
    @Provides
    @Singleton
    fun provideBaseUrl(properties: Properties): String {
        return properties.getProperty(CONSTANTS.BASE_URL, "default_base_url")
    }

    @Named(CONSTANTS.API_KEY)
    @Provides
    @Singleton
    fun provideApiKey(properties: Properties): String {
        return properties.getProperty(CONSTANTS.API_KEY, "default_api_key")
    }

    @Provides
    @Singleton
    fun provideRetrofit(@Named(CONSTANTS.BASE_URL) baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun provideCryptoApi(retrofit: Retrofit): CryptoApi {
        return retrofit.create(CryptoApi::class.java)
    }
}