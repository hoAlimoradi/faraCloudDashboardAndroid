package net.faracloud.dashboard.features.sensorDetails

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import net.faracloud.dashboard.core.BuilderViewModel
import net.faracloud.dashboard.core.scheduler.SchedulersImpl
import net.faracloud.dashboard.features.sensorDetails.observation.ObservationRecycleViewRowEntity
import javax.inject.Inject

@HiltViewModel
class SensorDetailsViewModel @Inject constructor(
    var schedulers: SchedulersImpl
) : BuilderViewModel<SensorDetailsState>(SensorDetailsState.IDLE) {

    val observationRecycleViewRowEntityListMutableLiveData =
        MutableStateFlow<List<ObservationRecycleViewRowEntity>?>(null)

    fun getObservations() {
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

}