package net.faracloud.dashboard.core.di


import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import net.faracloud.dashboard.core.scheduler.SchedulersModule
import net.faracloud.dashboard.core.sharedpreferences.SharedPreferenceModule
import net.faracloud.dashboard.features.componentList.di.ComponentModule
import net.faracloud.dashboard.features.map.di.MapModule
import net.faracloud.dashboard.features.splash.di.SplashModule
import net.faracloud.dashboard.features.providers.di.ProviderModule
import net.faracloud.dashboard.features.search.di.SearchModule
import net.faracloud.dashboard.features.sensorDetails.di.ObservationModule
import net.faracloud.dashboard.features.sensorsList.di.SensorModule
import net.faracloud.dashboard.features.setting.di.SettingModule
import net.faracloud.dashboard.features.statistics.di.StatisticsModule

@Module(
    includes = [
        SharedPreferenceModule::class,
        SplashModule::class,
        //DataBaseModule:: class,
        MapModule::class,
        StatisticsModule::class,
        ProviderModule::class,
        ComponentModule::class,
        SensorModule::class,
        ObservationModule::class,
        SettingModule::class,
        SearchModule::class,
        //ApiServiceFactoryModule::class,
        SchedulersModule::class
    ]
)
@InstallIn(ActivityRetainedComponent::class)
abstract class CoreModule