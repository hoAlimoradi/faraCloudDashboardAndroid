package net.faracloud.dashboard.features.sensorsList

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import net.faracloud.dashboard.core.BuilderViewModel
import net.faracloud.dashboard.core.scheduler.SchedulersImpl
import net.faracloud.dashboard.features.splash.presentation.SplashState
import javax.inject.Inject

@HiltViewModel
class SensorsListViewModel @Inject constructor(
    var schedulers: SchedulersImpl
) : BuilderViewModel<SensorsListState>(SensorsListState.IDLE) {

}