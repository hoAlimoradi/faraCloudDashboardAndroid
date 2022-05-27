package net.faracloud.dashboard.features.statistics.di

import net.faracloud.dashboard.features.statistics.data.StatisticsRepository
import net.faracloud.dashboard.features.statistics.data.StatisticsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class StatisticsModule {

    /**
     * Bind  Repository
     */
    @Binds
    abstract fun registerRepo(repository: StatisticsRepositoryImpl): StatisticsRepository
}