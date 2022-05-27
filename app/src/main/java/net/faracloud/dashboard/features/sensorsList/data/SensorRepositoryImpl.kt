package net.faracloud.dashboard.features.sensorsList.data

import androidx.lifecycle.LiveData
import net.faracloud.dashboard.core.api.ProviderService
import net.faracloud.dashboard.core.api.RemoteModelStats
import net.faracloud.dashboard.core.database.ProviderDao
import net.faracloud.dashboard.core.database.SensorDao
import net.faracloud.dashboard.core.database.SensorEntity
import net.faracloud.dashboard.core.model.RemoteModelProvider
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SensorRepositoryImpl @Inject constructor(
    private val providerService: ProviderService,
    private val sensorDao: SensorDao
): SensorRepository {

    override suspend fun getSensorsFromApi(providerId: String): Response<RemoteModelProvider> {
        return providerService.getCatalog(providerId)
    }

}