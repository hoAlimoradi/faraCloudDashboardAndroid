package net.faracloud.dashboard.features.map.data

import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import net.faracloud.dashboard.core.api.ProviderService
import net.faracloud.dashboard.core.database.ComponentDao
import net.faracloud.dashboard.core.database.ProviderDao
import net.faracloud.dashboard.core.database.ProviderEntity
import net.faracloud.dashboard.features.map.domain.MapRepository
import javax.inject.Inject

class MapRepositoryImpl @Inject constructor(
    private val providerDao: ProviderDao,
    private val componentDao: ComponentDao
) : MapRepository {
    var providerId = "mobile-app@p101"
    var authorizationToken = "1036124625b0f33d8057b7092a27e2ba3f9925e57e7a412f6e62253f9e63b8df"


    override suspend fun getComponentsByProviderIdFromDateBase(): Flow<List<ComponentRepoModel>?>  = flow {

        emit(null)

    }

/*    override suspend fun getAllProviders(): Flow<List<ProviderEntity>> = flow {
        providerDao.getProviders()
    }

    override suspend fun insertProvider(provider: ProviderEntity) = providerDao.insertProvider(provider)

    override suspend fun deleteProvider(provider: ProviderEntity) = providerDao.delete(provider)

    override suspend fun deleteAllProviders() = providerDao.deleteProviders()*/
    /*
     ,
     private val providerService: ProviderService,
     private val observationListRemoteRepoMapper: Mapper<@JvmSuppressWildcards List<ObservationRemoteModel>,
             @JvmSuppressWildcards List<ObservationRepoModel>>,

     override suspend fun getSensorObservationsFromApi(
         providerId: String,
         sensorId: String
     ): Flow<List<ObservationRepoModel>> {
         getResourceFromApiResponse(
             providerService.getObservations()
         ) {
             observationListRemoteRepoMapper.map(it.data)
         }


     }*/
}