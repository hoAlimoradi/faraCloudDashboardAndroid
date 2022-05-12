package net.faracloud.dashboard.features.splash.presentation

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_splash.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import net.faracloud.dashboard.R
import net.faracloud.dashboard.core.BuilderFragment
import net.faracloud.dashboard.core.BuilderViewModel
import net.faracloud.dashboard.extentions.loge


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

        viewModel.getConfig()

    }



    private fun setVersionCode() {
        viewModel.viewModelScope.launch {
            /*
            viewModel.getVersionCode()
                .catch { e ->
                    setVersionText(String().space)
                }
                .flowOn(Dispatchers.Default)
                .collect {
                    setVersionText(it)
                }
             */

        }
    }

    private fun setVersionText(text: String) {
        //versionCodeTextView.text = text
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
                /*getFindViewController()?.navigateUp()
                getFindViewController()?.navigate(R.id.navigateFromSplashToOnBoardingFragment)*/
            }
            SplashState.START_LOGIN -> {
                loge("START_LOGIN")
                getFindViewController()?.navigateUp()
                getFindViewController()?.navigate(R.id.navigateFromSplashFragmentToLoginFragment)
            }
            SplashState.START_HOME -> {
                loge("START_MAIN_ACTIVITY")
                getFindViewController()?.navigateUp()
                getFindViewController()?.navigate(R.id.navigateFromSplashFragmentToMapFragment)
            }
        }
    }
}


