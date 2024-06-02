package com.apx8.mongoose.di

import android.app.Application
import android.content.Context
import com.apx8.mongoose.preference.PrefManager
import com.apx8.mongoose.preference.PrefManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


/**
 * AppModule
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun bindContext(application: Application): Context

    @Binds
    @Singleton
    abstract fun bindPreferences(impl: PrefManagerImpl): PrefManager

}