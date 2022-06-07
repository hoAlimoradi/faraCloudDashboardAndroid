package net.faracloud.dashboard.features.search.di
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import net.faracloud.dashboard.features.search.data.SearchRepository
import net.faracloud.dashboard.features.search.data.SearchRepositoryImpl

@Module
@InstallIn(ActivityComponent::class)
abstract class SearchModule {

    /**
     * Bind SettingRepository
     */
    @Binds
    abstract fun registerRepo(repository: SearchRepositoryImpl): SearchRepository
}