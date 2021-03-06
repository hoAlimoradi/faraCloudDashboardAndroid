package net.faracloud.dashboard.core.api
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
class ApiServiceFactoryModule{
    @Provides
    @Reusable
    fun providerApiService(apiService: ApiServiceFactory) =
        apiService.create(ProviderService::class.java)

    @Provides
    @Reusable
    fun authService(authService: ApiServiceFactory) =
        authService.create(AuthService::class.java)
}