package net.faracloud.dashboard.features.sensorDetails

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_sensor_details.*
import net.faracloud.dashboard.R
import net.faracloud.dashboard.core.BuilderFragment
import net.faracloud.dashboard.core.BuilderViewModel
import net.faracloud.dashboard.extentions.loge
import net.faracloud.dashboard.features.sensorDetails.observation.ObservationFilterBottomSheet


@AndroidEntryPoint
class SensorDetailsFragment: BuilderFragment<SensorDetailsState, SensorDetailsViewModel>() {

    val titlesArray = arrayOf(
        "Observations",
        "Informations",
        "Chart"
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

        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                //backPressed()
                findNavController().navigate(R.id.sensorDetailsFragmentActionPopBack)
            }
        })
        backButton.setOnClickListener {
            findNavController().navigate(R.id.sensorDetailsFragmentActionPopBack)
        }

        filterImageButton.setOnClickListener {
            showBottomSheetDialog()
        }
        //.supportFragmentManager
        val adapter = SensorDetailsViewPagerAdapter(requireFragmentManager(), lifecycle)
        viewPagerSensorDetails.adapter = adapter

        TabLayoutMediator(tabLayoutSensorDetails, viewPagerSensorDetails) { tab, position ->
            tab.text = titlesArray[position]
        }.attach()
    }

    // creating a variable for our button
    private fun showBottomSheetDialog() {
        this.activity?.let {
            val fragment = ObservationFilterBottomSheet.newInstance()
            fragment.show(it.supportFragmentManager, "ObservationFilterBottomSheet")
        }

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


