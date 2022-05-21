package net.faracloud.dashboard.features.map.domain.usecase

import kotlinx.coroutines.flow.Flow
import net.faracloud.dashboard.core.useCase.BaseObserveUseCase
import net.faracloud.dashboard.features.map.data.ObservationRepoModel
import net.faracloud.dashboard.features.map.domain.MapRepository
import javax.inject.Inject

/* insertProviderUseCase
class GetObservationsUseCase @Inject constructor(
    private val repository: MapRepository
) : BaseObserveUseCase<List<ObservationRepoModel>>() {

    override suspend fun observe(): Flow<List<ObservationRepoModel>> {
        return repository.getSensorObservationsFromApi(
            providerId = "",
            sensorId = ""
        )
    }
}*/
