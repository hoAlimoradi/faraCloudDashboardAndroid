package net.faracloud.dashboard.features.map.di

import net.faracloud.dashboard.features.map.data.MapRepositoryImpl
import net.faracloud.dashboard.features.map.data.ObservationRepoModel
import net.faracloud.dashboard.features.map.domain.MapRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import net.faracloud.dashboard.core.useCase.BaseObserveUseCase
import net.faracloud.dashboard.features.splash.domain.usecase.CheckVersionUseCase

@Module
@InstallIn(ActivityComponent::class)
abstract class MapModule {

    /**
     * Bind MapRepository
     */
    @Binds
    abstract fun registerRepo(repository: MapRepositoryImpl): MapRepository


 /*   *//**
     * Bind CheckForTheFirstTimeAppIsLaunchedUseCase
     *//*
    @Binds
    abstract fun getObservationsUseCase(useCase: GetObservationsUseCase):
            BaseObserveUseCase<List<ObservationRepoModel>>*/

/*    *//**
     * Bind CheckVersionUseCase
     *//*
    @Binds
    abstract fun bindCheckVersionUseCase(useCase: CheckVersionUseCase):
            BaseObserveUseCase<String>*/

}
