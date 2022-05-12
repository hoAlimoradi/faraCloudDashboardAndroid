package net.faracloud.dashboard.core.api
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import net.faracloud.dashboard.features.splash.data.network.SplashApi

@Module
@InstallIn(ActivityRetainedComponent::class)
class ApiServiceFactoryModule{
    @Provides
    @Reusable
    fun splashApiService(apiService: ApiServiceFactory) =
        apiService.create(SplashApi::class.java)

    @Provides
    @Reusable
    fun authService(authService: ApiServiceFactory) =
        authService.create(AuthService::class.java)
}