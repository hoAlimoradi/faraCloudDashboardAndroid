package net.faracloud.dashboard.features.sensorDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import net.faracloud.dashboard.R

class ObservationListFragment : Fragment() {

    companion object {
        fun newInstance() = ObservationListFragment()
    }

    //private lateinit var viewModel: SensorDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sensor_observations, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //viewModel = ViewModelProvider(this).get(SensorDetailsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}