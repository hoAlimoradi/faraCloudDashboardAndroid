package net.faracloud.dashboard.features.map.presentation


import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import net.faracloud.dashboard.core.BuilderViewModel
import net.faracloud.dashboard.core.scheduler.SchedulersImpl
import net.faracloud.dashboard.features.map.domain.MapRepository
import net.faracloud.dashboard.features.map.presentation.MapState
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    var schedulers: SchedulersImpl,
    private val repository: MapRepository
) : BuilderViewModel<MapState>(MapState.IDLE) {

    fun navigateToSetting() {
        viewModelScope.launch {
            //delay(1000)
            state.value = MapState.START_SETTING
        }
    }

    fun navigateToDetail() {
        viewModelScope.launch {
            //delay(1000)
            state.value = MapState.START_DETAIL
        }
    }
}