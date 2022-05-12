package net.faracloud.dashboard.features.splash.presentation

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
class SplashViewModel @Inject constructor(
    var schedulers: SchedulersImpl
) : BuilderViewModel<SplashState>(SplashState.IDLE) {

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getConfig() {
        viewModelScope.launch {
            state.value = SplashState.LOADING
            delay(2000)
            state.value = SplashState.START_HOME

        }
    }
}