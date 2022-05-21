package net.faracloud.dashboard.features.splash.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import net.faracloud.dashboard.core.useCase.BaseObserveUseCase
import net.faracloud.dashboard.features.splash.data.SplashRepositoryImpl
import net.faracloud.dashboard.features.splash.domain.SplashRepository
import net.faracloud.dashboard.features.splash.domain.usecase.CheckForTheFirstTimeAppIsLaunchedUseCase
import net.faracloud.dashboard.features.splash.domain.usecase.CheckVersionUseCase

@Module
@InstallIn(ActivityComponent::class)
abstract class SplashModule {

    /**
     * Bind LoginRepository
     */
    @Binds
    abstract fun registerRepo(repository: SplashRepositoryImpl): SplashRepository


    /**
     * Bind CheckForTheFirstTimeAppIsLaunchedUseCase
     */
    @Binds
    abstract fun bindCheckForTheFirstTimeAppIsLaunchedUseCase(useCase: CheckForTheFirstTimeAppIsLaunchedUseCase):
            BaseObserveUseCase<Boolean>

    /**
     * Bind CheckVersionUseCase
     */
    @Binds
    abstract fun bindCheckVersionUseCase(useCase: CheckVersionUseCase):
            BaseObserveUseCase<String>

}
