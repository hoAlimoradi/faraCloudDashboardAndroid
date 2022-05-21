package net.faracloud.dashboard.features.tenant.data

import androidx.lifecycle.LiveData
import net.faracloud.dashboard.core.api.ProviderService
import net.faracloud.dashboard.core.api.RemoteModelStats
import net.faracloud.dashboard.core.database.ProviderDao
import net.faracloud.dashboard.core.database.ProviderEntity
import retrofit2.Response
import retrofit2.http.Path
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TenantRepositoryImpl @Inject constructor(
    private val providerService: ProviderService,
    private val providerDao: ProviderDao): TenantRepository {

    override suspend fun getStats(name: String): Response<RemoteModelStats> {
        return providerService.getStats(name)
    }

    override fun getAllProviders(): LiveData<List<ProviderEntity>> =  providerDao.getProviders()

    override suspend fun insertProvider(provider: ProviderEntity) = providerDao.insertProvider(provider)

    override suspend fun deleteProvider(provider: ProviderEntity) = providerDao.delete(provider)

    override suspend fun deleteAllProviders() = providerDao.deleteProviders()
}
