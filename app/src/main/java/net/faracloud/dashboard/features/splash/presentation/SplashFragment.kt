package net.faracloud.dashboard.features.splash.presentation

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.faracloud.dashboard.R
import net.faracloud.dashboard.core.BuilderFragment
import net.faracloud.dashboard.core.BuilderViewModel
import net.faracloud.dashboard.extentions.loge
import net.faracloud.dashboard.features.providers.presentation.ProviderRecycleViewViewRowEntity


@AndroidEntryPoint
class SplashFragment : BuilderFragment<SplashState, SplashViewModel>() {

    private val viewModel: SplashViewModel by viewModels()

    override val baseViewModel: BuilderViewModel<SplashState>
        get() = viewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.viewModelScope.launch {
            viewModel.state.value = SplashState.LOADING
            delay(2000)
            checkForTheFirstTimeAppIsLaunched()
        }
    }

    private fun checkForTheFirstTimeAppIsLaunched() {
        viewModel.getProviders().observe(viewLifecycleOwner) {
            it?.let { data ->
                data?.let { list ->
                    if (list.isEmpty()) {
                        viewModel.state.value = SplashState.START_ADD_PROVIDER
                    } else {
                        viewModel.state.value = SplashState.START_HOME
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
    }


    override fun onStateChange(state: SplashState) {
        when (state) {
            SplashState.IDLE -> {
                loge("IDLE")
            }
            SplashState.LOADING -> {
                loge("LOADING")
            }
            SplashState.FORCE_UPDATE -> {
                loge("FORCE_UPDATE")
            }
            SplashState.NEW_VERSION_READY -> {
                loge("NEW_VERSION_READY")
            }
            SplashState.RETRY -> {
                loge("RETRY")
            }
            SplashState.START_ON_BOARDING -> {
                loge("START_ON_BOARDING")

            }
            SplashState.START_ADD_PROVIDER -> {
                loge("START_LOGIN")
                getFindViewController()?.navigateUp()
                getFindViewController()?.navigate(R.id.navigateFromSplashToProviderListFragment)
            }
            SplashState.START_HOME -> {
                loge("START_MAIN_ACTIVITY")
                getFindViewController()?.navigateUp()
                getFindViewController()?.navigate(R.id.navigateFromSplashFragmentToMapFragment)
            }
        }
    }
}


