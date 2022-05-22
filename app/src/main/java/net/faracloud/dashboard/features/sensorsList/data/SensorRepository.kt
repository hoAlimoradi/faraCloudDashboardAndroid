package net.faracloud.dashboard.features.sensorsList.data

import androidx.lifecycle.LiveData
import net.faracloud.dashboard.core.api.RemoteModelStats
import net.faracloud.dashboard.core.database.ProviderEntity
import net.faracloud.dashboard.core.database.SensorEntity
import retrofit2.Response

interface SensorRepository {

    fun getAllSensors(): LiveData<List<SensorEntity>>

    suspend fun insertProvider(provider: SensorEntity): Long

    suspend fun deleteProvider(provider: SensorEntity)

    suspend fun deleteAllProviders()

    suspend fun getSensorsFromApi(name: String): Response<RemoteModelStats>
}