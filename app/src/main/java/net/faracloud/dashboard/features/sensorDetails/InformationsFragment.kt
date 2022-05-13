package net.faracloud.dashboard.features.sensorDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import net.faracloud.dashboard.R

class InformationsFragment : Fragment() {

    companion object {
        fun newInstance() = InformationsFragment()
    }

    //private lateinit var viewModel: SensorDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sensor_infotmations, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //viewModel = ViewModelProvider(this).get(SensorDetailsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}