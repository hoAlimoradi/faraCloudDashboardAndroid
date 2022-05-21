package net.faracloud.dashboard.features.map.presentation

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_map.*
import net.faracloud.dashboard.R
import net.faracloud.dashboard.core.BuilderFragment
import net.faracloud.dashboard.core.BuilderViewModel
import net.faracloud.dashboard.extentions.loge


@AndroidEntryPoint
class MapFragment : BuilderFragment<MapState, MapViewModel>() {


    private val viewModel: MapViewModel by viewModels()

    override val baseViewModel: BuilderViewModel<MapState>
        get() = viewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonSetting.setOnClickListener {
          viewModel.navigateToSetting()
        }

        buttonDetail.setOnClickListener {
            viewModel.navigateToDetail()
        }

    }



    private fun setVersionText(text: String) {
        //versionCodeTextView.text = text
    }

    override fun onResume() {
        super.onResume()
    }


    override fun onStateChange(state: MapState) {
        when (state) {
            MapState.IDLE -> {
                loge("IDLE")
            }
            MapState.START_DETAIL -> {
                loge("START_DETAIL")
                getFindViewController()?.navigateUp()
                getFindViewController()?.navigate(R.id.navigateFromMapToProviderDetailsFragment)
            }
            MapState.START_SETTING -> {
                loge("FORCE_UPDATE")
                getFindViewController()?.navigateUp()
                getFindViewController()?.navigate(R.id.navigateFromMapToTenantFragment)
            }

        }
    }
}


