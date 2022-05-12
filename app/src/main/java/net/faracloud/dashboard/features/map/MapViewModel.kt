package net.faracloud.dashboard.features.map


import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import net.faracloud.dashboard.core.BuilderViewModel
import net.faracloud.dashboard.core.scheduler.SchedulersImpl
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    var schedulers: SchedulersImpl
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