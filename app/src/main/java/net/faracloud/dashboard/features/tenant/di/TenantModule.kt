package net.faracloud.dashboard.features.tenant.di

import net.faracloud.dashboard.features.tenant.data.TenantRepository
import net.faracloud.dashboard.features.tenant.data.TenantRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class TenantModule {
    /**
     * Bind TenantRepository
     */
    @Binds
    abstract fun registerRepo(repository: TenantRepositoryImpl): TenantRepository
}
