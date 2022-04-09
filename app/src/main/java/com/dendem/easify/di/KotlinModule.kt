package com.dendem.easify.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
@DelicateCoroutinesApi
object KotlinModule {

    @Provides
    @Singleton
    fun provideCoroutineScope(): CoroutineScope {
        return GlobalScope
    }
}
