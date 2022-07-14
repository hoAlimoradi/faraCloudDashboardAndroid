package net.faracloud.dashboard.features.sensorsList.data

import androidx.lifecycle.LiveData
import net.faracloud.dashboard.core.api.ProviderService
import net.faracloud.dashboard.core.database.SensorEntity
import net.faracloud.dashboard.core.database.doa.ComponentDao
import net.faracloud.dashboard.core.database.doa.SensorDao
import net.faracloud.dashboard.core.database.relations.ComponentsWithSensor
import net.faracloud.dashboard.core.database.relations.ProvidersWithComponent
import net.faracloud.dashboard.core.model.RemoteModelProviders
import net.faracloud.dashboard.core.sharedpreferences.PreferenceHelper
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SensorRepositoryImpl @Inject constructor(
    private val providerService: ProviderService,
    private val componentDao: ComponentDao,
    private val sensorDao: SensorDao,
    private val pref: PreferenceHelper
): SensorRepository {

    override suspend fun getSensorsFromApi(providerId: String,
                                           token: String): Response<RemoteModelProviders> {
        return providerService.getCatalog(token)
    }

    override fun setLastSensorId(sensorId: String) {
        pref.setLastSensorId(sensorId)
    }

    override fun getLastComponentId(): String {
        return pref.getLastComponentId()
    }

    override fun getLastProviderId(): String {
        return pref.getLastProviderId()
    }

    override fun getLastAuthorizationToken(): String {
        return pref.getLastAuthorizationToken()
    }

    override fun getAllSensors(): LiveData<List<SensorEntity>> = sensorDao.getSensors()

    override fun getComponentWithSensors(componentId: String): LiveData<List<ComponentsWithSensor>> {
        return componentDao.getComponentWithSensors(componentId)
    }

    override suspend fun insertSensors(sensor: SensorEntity)= sensorDao.insertSensor(sensor)

    override suspend fun deleteSensor(sensor: SensorEntity)  = sensorDao.updateSensor(sensor)

    override suspend fun deleteAllSensors() = sensorDao.deleteSensors()
}
