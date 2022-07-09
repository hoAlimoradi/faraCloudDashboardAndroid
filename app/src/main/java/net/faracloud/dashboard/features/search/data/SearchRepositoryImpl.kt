package net.faracloud.dashboard.features.search.data

import androidx.lifecycle.LiveData
import net.faracloud.dashboard.core.database.*
import net.faracloud.dashboard.core.database.doa.ComponentDao
import net.faracloud.dashboard.core.database.doa.ProviderDao
import net.faracloud.dashboard.core.database.doa.SensorDao
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