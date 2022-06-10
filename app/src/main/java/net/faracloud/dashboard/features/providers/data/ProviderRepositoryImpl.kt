package net.faracloud.dashboard.features.providers.data

import androidx.lifecycle.LiveData
import net.faracloud.dashboard.core.api.ProviderService
import net.faracloud.dashboard.core.database.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProviderRepositoryImpl @Inject constructor(
    private val providerService: ProviderService,
    private val providerDao: ProviderDao,
    private val componentDao: ComponentDao,
    private val sensorDao: SensorDao
)
    : ProviderRepository {

    override fun getAllProviders(): LiveData<List<ProviderEntity>> =  providerDao.getProviders()

    override fun getProviderByProviderId(providerId: String) = providerDao.getProviderByProviderId(providerId)

    override fun getProviderById(id: Int): LiveData<ProviderEntity> {
       return providerDao.getProviderById(id)
    }

    override suspend fun insertProvider(provider: ProviderEntity) = providerDao.insertProvider(provider)

    override suspend fun updateProvider(provider: ProviderEntity) {
        providerDao.updateProvider(provider)
    }

    override suspend fun deleteProvider(provider: ProviderEntity) = providerDao.delete(provider)

    override suspend fun deleteAllProviders() = providerDao.deleteProviders()

  /*  override fun getAllSensors(): LiveData<List<SensorEntity>> = sensorDao.getSensors()

    override fun getAllComponents(): LiveData<List<ComponentEntity>> = componentDao.getComponents()*/
}
