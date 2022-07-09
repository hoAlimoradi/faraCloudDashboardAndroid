package net.faracloud.dashboard.features.componentList.data

import androidx.lifecycle.LiveData
import net.faracloud.dashboard.core.api.ProviderService
import net.faracloud.dashboard.core.database.doa.ComponentDao
import net.faracloud.dashboard.core.database.ComponentEntity
import net.faracloud.dashboard.core.database.doa.SensorDao
import net.faracloud.dashboard.core.database.SensorEntity
import net.faracloud.dashboard.core.model.RemoteModelProviders
import net.faracloud.dashboard.core.sharedpreferences.PreferenceHelper
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ComponentRepositoryImpl @Inject constructor(
    private val providerService: ProviderService,
    private val componentDao: ComponentDao,
    private val sensorDao: SensorDao,
    private val pref: PreferenceHelper
): ComponentRepository {

    override suspend fun getSensorsFromApi(tenantName: String,
                                           providerId: String,
                                           token: String): Response<RemoteModelProviders> {
        val providerID = tenantName + "@" + providerId
       // return providerService.getCatalog(providerID,token)
        return providerService.getCatalog(token)
    }

    override fun getAllComponents(): LiveData<List<ComponentEntity>> = componentDao.getComponents()

    override suspend fun insertComponent(component: ComponentEntity) = componentDao.insertComponent(component)

    override suspend fun deleteComponent(component: ComponentEntity) = componentDao.delete(component = component)

    override suspend fun deleteAllComponents() = componentDao.deleteComponents()

    override fun getAllSensors(): LiveData<List<SensorEntity>> = sensorDao.getSensors()

    override suspend fun insertSensors(sensor: SensorEntity)= sensorDao.insertSensor(sensor)

    override suspend fun deleteAllSensors() = sensorDao.deleteSensors()
    override fun getLastTenantName(): String {
        return pref.getTenantName()
    }

    override fun setLastTenantName(tenantName: String) {
        return pref.setTenantName(tenantName)
    }

    override fun getLastProviderId(): String {
        return pref.getLastSensorId()
    }

    override fun setLastProviderId(lastProviderId: String) {
        return pref.setLastProviderId(lastProviderId)
    }

    override fun getLastAuthorizationToken(): String {
        return pref.getLastAuthorizationToken()
    }

    override fun setLastAuthorizationToken(lastAuthorizationToken: String) {
        pref.setLastAuthorizationToken(lastAuthorizationToken)
    }
}