package net.faracloud.dashboard.features.sensorDetails

/*
import androidx.lifecycle.ViewModel

class SensorDetailsViewModel : ViewModel() {
    // TODO: Implement the ViewModel
}

 */

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import net.faracloud.dashboard.core.BuilderViewModel
import net.faracloud.dashboard.core.scheduler.SchedulersImpl
import net.faracloud.dashboard.features.map.MapState
import javax.inject.Inject

@HiltViewModel
class SensorDetailsViewModel @Inject constructor(
    var schedulers: SchedulersImpl
) : BuilderViewModel<SensorDetailsState>(SensorDetailsState.IDLE) {


}