package net.faracloud.dashboard.core.di


import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import net.faracloud.dashboard.core.api.ApiServiceFactoryModule
import net.faracloud.dashboard.core.database.DataBaseModule
import net.faracloud.dashboard.core.scheduler.SchedulersModule
import net.faracloud.dashboard.core.sharedpreferences.SharedPreferenceModule
import net.faracloud.dashboard.features.map.di.MapModule
import net.faracloud.dashboard.features.splash.di.SplashModule
import net.faracloud.dashboard.features.tenant.di.ProviderModule

@Module(
    includes = [
        SharedPreferenceModule::class,
        SplashModule::class,
        //DataBaseModule:: class,
        MapModule::class,
        ProviderModule::class,
        //ApiServiceFactoryModule::class,
        SchedulersModule::class
    ]
)
@InstallIn(ActivityRetainedComponent::class)
abstract class CoreModule