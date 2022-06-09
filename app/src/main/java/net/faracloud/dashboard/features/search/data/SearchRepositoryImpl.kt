package net.faracloud.dashboard.features.search.data

import androidx.lifecycle.LiveData
import net.faracloud.dashboard.core.database.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchRepositoryImpl @Inject constructor(
    private val providerDao: ProviderDao,
    private val sensorDao: SensorDao,
    private val componentDao: ComponentDao,
): SearchRepository {

    override fun getSensors(): LiveData<List<SensorEntity>> {
        return sensorDao.getSensors()
    }

    override fun getComponents(): LiveData<List<ComponentEntity>> {
        return componentDao.getComponents()
    }

    override fun getProviders(): LiveData<List<ProviderEntity>> {
        return providerDao.getProviders()
    }

}