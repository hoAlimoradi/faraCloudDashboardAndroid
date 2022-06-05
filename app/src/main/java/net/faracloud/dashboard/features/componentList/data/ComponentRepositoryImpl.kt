package net.faracloud.dashboard.features.componentList.data

import net.faracloud.dashboard.features.sensorsList.data.SensorRepository

import androidx.lifecycle.LiveData
import net.faracloud.dashboard.core.api.ProviderService
import net.faracloud.dashboard.core.database.ComponentDao
import net.faracloud.dashboard.core.database.ComponentEntity
import net.faracloud.dashboard.core.database.SensorDao
import net.faracloud.dashboard.core.database.SensorEntity
import net.faracloud.dashboard.core.model.RemoteModelProvider
import net.faracloud.dashboard.core.model.RemoteModelProviders
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ComponentRepositoryImpl @Inject constructor(
    private val providerService: ProviderService,
    private val componentDao: ComponentDao,
    private val sensorDao: SensorDao
): ComponentRepository {

    override suspend fun getSensorsFromApi(providerId: String,
                                           token: String): Response<RemoteModelProviders> {
        return providerService.getCatalog(providerId,token)
    }

    override fun getAllComponents(): LiveData<List<ComponentEntity>> = componentDao.getComponents()

    override suspend fun insertComponent(component: ComponentEntity) = componentDao.insertComponent(component)

    override suspend fun deleteComponent(component: ComponentEntity) = componentDao.delete(component = component)

    override suspend fun deleteAllComponents() = componentDao.deleteComponents()

    override fun getAllSensors(): LiveData<List<SensorEntity>> = sensorDao.getSensors()

    override suspend fun insertSensors(sensor: SensorEntity)= sensorDao.insertSensor(sensor)

    override suspend fun deleteAllSensors() = sensorDao.deleteSensors()

}