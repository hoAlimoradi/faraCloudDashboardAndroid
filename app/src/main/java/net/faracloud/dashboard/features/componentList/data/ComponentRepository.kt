package net.faracloud.dashboard.features.componentList.data

import androidx.lifecycle.LiveData
import net.faracloud.dashboard.core.database.ComponentEntity
import net.faracloud.dashboard.core.database.SensorEntity
import net.faracloud.dashboard.core.model.RemoteModelProvider
import net.faracloud.dashboard.core.model.RemoteModelProviders
import retrofit2.Response

interface ComponentRepository {

    suspend fun getSensorsFromApi(providerId: String,token: String): Response<RemoteModelProviders>

    fun getAllComponents(): LiveData<List<ComponentEntity>>

    suspend fun insertComponent(component: ComponentEntity): Long

    suspend fun deleteComponent(component: ComponentEntity)

    suspend fun deleteAllComponents()

    //Sensor
    fun getAllSensors(): LiveData<List<SensorEntity>>

    suspend fun insertSensors(sensor: SensorEntity): Long

    suspend fun deleteAllSensors()
}
