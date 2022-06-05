package net.faracloud.dashboard.features.sensorsList.data

import androidx.lifecycle.LiveData
import net.faracloud.dashboard.core.api.RemoteModelStats
import net.faracloud.dashboard.core.database.ProviderEntity
import net.faracloud.dashboard.core.database.SensorEntity
import net.faracloud.dashboard.core.model.RemoteModelProvider
import net.faracloud.dashboard.core.model.RemoteModelProviders
import retrofit2.Response

interface SensorRepository {

    fun getAllSensors(): LiveData<List<SensorEntity>>

    suspend fun insertSensors(sensor: SensorEntity): Long

    suspend fun deleteSensor(sensor: SensorEntity)

    suspend fun deleteAllSensors()


    suspend fun getSensorsFromApi(providerId: String,token: String): Response<RemoteModelProviders>
}