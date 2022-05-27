package net.faracloud.dashboard.features.sensorsList.data

import androidx.lifecycle.LiveData
import net.faracloud.dashboard.core.api.RemoteModelStats
import net.faracloud.dashboard.core.database.ProviderEntity
import net.faracloud.dashboard.core.database.SensorEntity
import net.faracloud.dashboard.core.model.RemoteModelProvider
import retrofit2.Response

interface SensorRepository {

 /*   fun getAllSensors(): LiveData<List<SensorEntity>>

    suspend fun insertProvider(provider: SensorEntity): Long

    suspend fun deleteProvider(provider: SensorEntity)

    suspend fun deleteAllProviders()*/

    suspend fun getSensorsFromApi(providerId: String): Response<RemoteModelProvider>
}