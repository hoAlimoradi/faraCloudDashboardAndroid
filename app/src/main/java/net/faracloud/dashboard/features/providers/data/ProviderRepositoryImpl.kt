package net.faracloud.dashboard.features.providers.data

import androidx.lifecycle.LiveData
import net.faracloud.dashboard.core.api.ProviderService
import net.faracloud.dashboard.core.database.ProviderDao
import net.faracloud.dashboard.core.database.ProviderEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProviderRepositoryImpl @Inject constructor(
    private val providerService: ProviderService,
    private val providerDao: ProviderDao): ProviderRepository {

    override fun getAllProviders(): LiveData<List<ProviderEntity>> =  providerDao.getProviders()

    override suspend fun insertProvider(provider: ProviderEntity) = providerDao.insertProvider(provider)

    override suspend fun deleteProvider(provider: ProviderEntity) = providerDao.delete(provider)

    override suspend fun deleteAllProviders() = providerDao.deleteProviders()
}
