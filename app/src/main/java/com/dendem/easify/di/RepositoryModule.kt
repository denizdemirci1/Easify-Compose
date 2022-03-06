package com.dendem.easify.di

import com.dendem.easify.data.remote.SpotifyApi
import com.dendem.easify.data.repository.PersonalizationRepositoryImpl
import com.dendem.easify.data.repository.PlayerRepositoryImpl
import com.dendem.easify.domain.repository.PersonalizationRepository
import com.dendem.easify.domain.repository.PlayerRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun providePlayerRepository(api: SpotifyApi): PlayerRepository {
        return PlayerRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun providePersonalizationRepository(api: SpotifyApi): PersonalizationRepository {
        return PersonalizationRepositoryImpl(api)
    }
}
