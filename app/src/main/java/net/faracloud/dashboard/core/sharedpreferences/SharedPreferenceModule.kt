package net.faracloud.dashboard.core.sharedpreferences

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class SharedPreferenceModule {

    @Binds
    abstract fun bindSharedPreferences(preferencesImpl: AppPreferencesImpl): PreferenceHelper

}