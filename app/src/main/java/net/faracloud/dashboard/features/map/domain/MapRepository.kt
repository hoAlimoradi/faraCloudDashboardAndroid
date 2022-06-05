package net.faracloud.dashboard.features.map.domain

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import net.faracloud.dashboard.core.database.ComponentEntity
import net.faracloud.dashboard.core.database.ProviderEntity
import net.faracloud.dashboard.features.map.data.ComponentRepoModel
import net.faracloud.dashboard.features.map.data.ObservationRepoModel

interface MapRepository {

    fun getAllComponents(): LiveData<List<ComponentEntity>>
/*    suspend fun getAllProviders(): Flow<List<ProviderEntity>>

    suspend fun insertProvider(provider: ProviderEntity)

    suspend fun deleteProvider(provider: ProviderEntity)

    suspend fun deleteAllProviders()*/

/*
suspend fun getComponentsByProviderIdFromApi(providerId: String): Flow<List<ComponentRepoModel>?>
suspend fun getSensorObservationsFromApi(providerId: String,
                                             sensorId: String): Flow<List<ObservationRepoModel>?>*/
}