package net.faracloud.dashboard.features.sensorsList.data

import androidx.lifecycle.LiveData
import net.faracloud.dashboard.core.api.ProviderService
import net.faracloud.dashboard.core.api.RemoteModelStats
import net.faracloud.dashboard.core.database.ProviderDao
import net.faracloud.dashboard.core.database.SensorDao
import net.faracloud.dashboard.core.database.SensorEntity
import net.faracloud.dashboard.core.model.RemoteModelProvider
import net.faracloud.dashboard.core.model.RemoteModelProviders
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SensorRepositoryImpl @Inject constructor(
    private val providerService: ProviderService,
    private val sensorDao: SensorDao
): SensorRepository {

    override suspend fun getSensorsFromApi(providerId: String,
                                           token: String): Response<RemoteModelProviders> {
        return providerService.getCatalog(providerId,token)
    }

    override fun getAllSensors(): LiveData<List<SensorEntity>> = sensorDao.getSensors()

    override suspend fun insertSensors(sensor: SensorEntity)= sensorDao.insertSensor(sensor)

    override suspend fun deleteSensor(sensor: SensorEntity)  = sensorDao.updateSensor(sensor)

    override suspend fun deleteAllSensors() = sensorDao.deleteSensors()
}