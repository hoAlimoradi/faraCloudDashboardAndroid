package net.faracloud.dashboard.features.splash.presentation

import android.content.Context
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import net.faracloud.dashboard.core.BuilderViewModel
import net.faracloud.dashboard.core.scheduler.SchedulersImpl
import net.faracloud.dashboard.features.providers.data.ProviderRepository
import net.faracloud.dashboard.features.splash.domain.SplashRepository
import net.faracloud.dashboard.features.splash.domain.usecase.CheckForTheFirstTimeAppIsLaunchedUseCase
import net.faracloud.dashboard.features.splash.domain.usecase.CheckVersionUseCase
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    var schedulers: SchedulersImpl,
    private val repository: SplashRepository,
    @ApplicationContext private val context: Context,
    var checkForTheFirstTimeAppIsLaunchedUseCase: CheckForTheFirstTimeAppIsLaunchedUseCase,
    var checkVersionUseCase: CheckVersionUseCase
) : BuilderViewModel<SplashState>(SplashState.IDLE) {

    fun getProviders() = repository.getAllProviders()

}

/*

    @OptIn(ExperimentalCoroutinesApi::class)
    fun checkForTheFirstTimeAppIsLaunched() {
        viewModelScope.launch {
            state.value = SplashState.LOADING
            delay(2000)
            state.value = SplashState.START_HOME
        }
    }
 */