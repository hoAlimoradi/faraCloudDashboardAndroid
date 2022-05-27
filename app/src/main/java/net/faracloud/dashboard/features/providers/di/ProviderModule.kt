package net.faracloud.dashboard.features.providers.di

import net.faracloud.dashboard.features.providers.data.ProviderRepository
import net.faracloud.dashboard.features.providers.data.ProviderRepositoryImpl

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class ProviderModule {

    /**
     * Bind ProviderRepository
     */
    @Binds
    abstract fun registerRepo(repository: ProviderRepositoryImpl): ProviderRepository
}