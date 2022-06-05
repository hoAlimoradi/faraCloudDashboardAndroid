package net.faracloud.dashboard.features.sensorsList.di

import net.faracloud.dashboard.features.sensorsList.data.SensorRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import net.faracloud.dashboard.features.sensorsList.data.SensorRepositoryImpl

@Module
@InstallIn(ActivityComponent::class)
abstract class SensorModule {

    /**
     * Bind ProviderRepository
     */
    @Binds
    abstract fun registerRepo(repository: SensorRepositoryImpl): SensorRepository
}
