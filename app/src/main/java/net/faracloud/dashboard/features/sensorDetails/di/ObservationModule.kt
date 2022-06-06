package net.faracloud.dashboard.features.sensorDetails.di

import net.faracloud.dashboard.features.sensorDetails.data.SensorDetailsRepository
import net.faracloud.dashboard.features.sensorDetails.data.SensorDetailsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class ObservationModule {
    /**
     * Bind  ObservationRepository
     */
    @Binds
    abstract fun registerRepo(repository: SensorDetailsRepositoryImpl): SensorDetailsRepository
}
