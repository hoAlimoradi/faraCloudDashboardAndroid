package net.faracloud.dashboard.core.di


import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import net.faracloud.dashboard.core.api.ApiServiceFactoryModule
import net.faracloud.dashboard.core.database.DataBaseModule
import net.faracloud.dashboard.core.scheduler.SchedulersModule
import net.faracloud.dashboard.core.sharedpreferences.SharedPreferenceModule
import net.faracloud.dashboard.features.splash.di.SplashModule

@Module(
    includes = [
        SharedPreferenceModule::class,
        SplashModule::class,
        //DataBaseModule:: class,
        //LoginModule::class,
        ApiServiceFactoryModule::class,
        SchedulersModule::class
    ]
)
@InstallIn(ActivityRetainedComponent::class)
abstract class CoreModule