
package net.faracloud.dashboard.features.componentList.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import net.faracloud.dashboard.features.componentList.data.ComponentRepository
import net.faracloud.dashboard.features.componentList.data.ComponentRepositoryImpl

@Module
@InstallIn(ActivityComponent::class)
abstract class ComponentModule {


     /**
     * Bind ComponentRepository
     */

    @Binds
    abstract fun registerRepo(repository: ComponentRepositoryImpl): ComponentRepository
}

