package com.dendem.easify.di

import com.dendem.easify.util.storage.SharedPreferencesStorage
import com.dendem.easify.util.storage.Storage
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class StorageModule {

    @Binds
    @Singleton
    abstract fun provideStorage(storage: SharedPreferencesStorage): Storage
}
