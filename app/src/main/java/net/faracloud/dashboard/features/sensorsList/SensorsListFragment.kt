package net.faracloud.dashboard.features.sensorsList

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.hilt.android.AndroidEntryPoint
import net.faracloud.dashboard.R

@AndroidEntryPoint
class SensorsListFragment : Fragment() {

    companion object {
        fun newInstance() = SensorsListFragment()
    }

    private lateinit var viewModel: SensorsListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sensors_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SensorsListViewModel::class.java)
        // TODO: Use the ViewModel
    }

}