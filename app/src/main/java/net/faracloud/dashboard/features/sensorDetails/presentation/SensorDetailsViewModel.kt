package net.faracloud.dashboard.features.sensorDetails.presentation

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import net.faracloud.dashboard.core.BuilderViewModel
import net.faracloud.dashboard.core.api.NetworkUtil
import net.faracloud.dashboard.core.database.SensorEntity
import net.faracloud.dashboard.core.database.SensorObservationEntity
import net.faracloud.dashboard.core.database.relations.SensorsWithObservation
import net.faracloud.dashboard.core.scheduler.SchedulersImpl
import net.faracloud.dashboard.extentions.loge
import net.faracloud.dashboard.features.sensorDetails.data.SensorDetailsRepository
import net.faracloud.dashboard.features.sensorDetails.presentation.observation.ObservationRecycleViewRowEntity
import javax.inject.Inject

@HiltViewModel
class SensorDetailsViewModel @Inject constructor(
    var schedulers: SchedulersImpl,
    private val repository: SensorDetailsRepository,
    @ApplicationContext private val context: Context
) : BuilderViewModel<SensorDetailsState>(SensorDetailsState.IDLE) {

    val observationRecycleViewRowEntityListMutableLiveData =
        MutableStateFlow<List<ObservationRecycleViewRowEntity>?>(null)

    fun getSensorsFromDataBase() = repository.getAllSensors()

    fun getSensorFromDataBase():LiveData<SensorEntity> {
        return repository.getSensor(repository.getLastSensorId())
    }
    fun getSensorsWithObservation(): LiveData<List<SensorsWithObservation>> {
        return repository.getSensorsWithObservation(repository.getLastSensorId())
    }

    suspend fun getObservationsFromApi(
        categoryNumber: Int?,
        startDate: String?,
        endDate: String?
    ) {

        state.value = SensorDetailsState.LOADING
        try {
            if (NetworkUtil.hasInternetConnection(context)) {

                val response = repository.getObservationsFromApi(
                    token =  repository.getLastAuthorizationToken(),
                    providerId =  repository.getLastProviderId(),
                    sensor = repository.getLastSensorId(),
                    categoryNumber = 10,
                    startDate = startDate,
                    endDate = endDate
                )
                if (response.isSuccessful) {
                    response.body()?.let { resultResponse ->
                        // TODO saveto database
                        loge(resultResponse.toString())
                        var sensorObservationEntityList: MutableList<SensorObservationEntity> =
                            mutableListOf()
                        var observationRecycleViewRowEntityList: MutableList<ObservationRecycleViewRowEntity> =
                            mutableListOf()
                        resultResponse.observations.forEach {
                            val sensorLocationArray = it.location.split(" ").toTypedArray()
                            val latitude: Double = sensorLocationArray[0].toDouble()
                            val longitude: Double = sensorLocationArray[1].toDouble()
                            sensorObservationEntityList.add(
                                SensorObservationEntity(
                                    latitude = latitude,
                                    longitude = longitude,
                                    sensor = repository.getLastSensorId(),
                                    time = it.time,
                                    timestamp = null,
                                    value = it.value.toLong(),
                                    lastUpdateDevice = null
                                )
                            )
                            observationRecycleViewRowEntityList.add(
                                ObservationRecycleViewRowEntity(
                                    value = it.value,
                                    timestamp = it.timestamp,
                                    latitude = latitude,
                                    longitude = longitude,
                                    time = it.time.toString()
                                )
                            )
                        }
                        saveObservations(sensorObservationEntityList)
                        observationRecycleViewRowEntityListMutableLiveData.value =
                            observationRecycleViewRowEntityList
                    }
                }
                state.value = SensorDetailsState.IDLE
            } else {
                state.value = SensorDetailsState.RETRY
            }
        } catch (ex: Exception) {
            loge(ex.message)
            loge(ex.localizedMessage)
            state.value = SensorDetailsState.RETRY

        }
    }

    private fun saveObservations(observations: List<SensorObservationEntity>) {
        viewModelScope.launch {
            repository.deleteAllObservations()
            observations.forEach {
                repository.insertObservation(it)
            }
        }
    }

    fun getObservationsFromDataBase() = repository.getAllObservations()

}










/*private fun observationRemoteModelToObservationRecycleViewRowEntityMapper(observationRemoteModels : List<ObservationRemoteModel>): List<ObservationRecycleViewRowEntity> {
fun getObservations1() {
    viewModelScope.launch {
        observationRecycleViewRowEntityListMutableLiveData.value = createMockObservations()
    }

}

private fun createMockObservations(): List<ObservationRecycleViewRowEntity> {

    var mockObservations: MutableList<ObservationRecycleViewRowEntity> = mutableListOf()

    val observationModel1 = ObservationRecycleViewRowEntity(
        value = "Devices",
        timestamp = "8",
        latitude = 34.6666,
        longitude = 56.666,
        time = "0 "
    )

    val observationModel2 = ObservationRecycleViewRowEntity(
        value = "Devices",
        timestamp = "8",
        latitude = 34.6666,
        longitude = 56.666,
        time = "0 "
    )

    val observationModel3 = ObservationRecycleViewRowEntity(
        value = "Devices",
        timestamp = "8",
        latitude = 34.6666,
        longitude = 56.666,
        time = "0 "
    )

    val observationModel4 = ObservationRecycleViewRowEntity(
        value = "Devices",
        timestamp = "8",
        latitude = 34.6666,
        longitude = 56.666,
        time = "0 "
    )

    mockObservations.add(observationModel1)
    mockObservations.add(observationModel2)
    mockObservations.add(observationModel3)
    mockObservations.add(observationModel4)

    return mockObservations
}
}*/

