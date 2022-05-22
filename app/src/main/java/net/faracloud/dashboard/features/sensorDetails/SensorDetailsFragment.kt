package net.faracloud.dashboard.features.sensorDetails

import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_map.*
import kotlinx.android.synthetic.main.fragment_sensor_details.*
import net.faracloud.dashboard.R
import net.faracloud.dashboard.core.BuilderFragment
import net.faracloud.dashboard.core.BuilderViewModel
import net.faracloud.dashboard.extentions.loge

@AndroidEntryPoint
class SensorDetailsFragment: BuilderFragment<SensorDetailsState, SensorDetailsViewModel>() {

    val titlesArray = arrayOf(
        "Chart",
        "Informations",
        "Observations"
    )
    private val viewModel: SensorDetailsViewModel by viewModels()

    override val baseViewModel: BuilderViewModel<SensorDetailsState>
        get() = viewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sensor_details, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        backButton.setOnClickListener {
            findNavController().navigate(R.id.sensorDetailsFragmentActionPopBack)
        }

        //.supportFragmentManager
        val adapter = SensorDetailsViewPagerAdapter(requireFragmentManager(), lifecycle)
        viewPagerSensorDetails.adapter = adapter

        TabLayoutMediator(tabLayoutSensorDetails, viewPagerSensorDetails) { tab, position ->
            tab.text = titlesArray[position]
        }.attach()
    }


    private fun setVersionText(text: String) {
        //versionCodeTextView.text = text
    }

    override fun onResume() {
        super.onResume()
    }


    override fun onStateChange(state: SensorDetailsState) {
        when (state) {
            SensorDetailsState.IDLE -> {
                loge("IDLE")
            }
        }
    }
}


