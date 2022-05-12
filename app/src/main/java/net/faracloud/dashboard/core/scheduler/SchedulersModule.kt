package net.faracloud.dashboard.core.scheduler

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class SchedulersModule {

    @Binds
    abstract fun bindSchedulers(impl: SchedulersImpl): Schedulers

}