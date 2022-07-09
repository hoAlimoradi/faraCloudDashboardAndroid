package net.faracloud.dashboard.features.setting.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import net.faracloud.dashboard.features.setting.data.SettingRepository
import net.faracloud.dashboard.features.setting.data.SettingRepositoryImpl

@Module
@InstallIn(ActivityComponent::class)
abstract class SettingModule {
    /**
     * Bind SettingRepository
     */
    @Binds
    abstract fun registerRepo(repository: SettingRepositoryImpl): SettingRepository
}
