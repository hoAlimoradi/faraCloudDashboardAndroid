package net.faracloud.dashboard.features.sensorsList.presentation

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_component.*
import kotlinx.android.synthetic.main.fragment_sensors_list.*
import kotlinx.android.synthetic.main.fragment_sensors_list.backButton
import net.faracloud.dashboard.R
import net.faracloud.dashboard.core.BuilderFragment
import net.faracloud.dashboard.core.BuilderViewModel
import net.faracloud.dashboard.extentions.loge
import net.faracloud.dashboard.features.BundleKeys
import net.faracloud.dashboard.features.componentList.presentation.ComponentAdapter
import net.faracloud.dashboard.features.componentList.presentation.ComponentState
import net.faracloud.dashboard.features.providers.presentation.ProviderRecycleViewViewRowEntity

@AndroidEntryPoint
class SensorsListFragment : BuilderFragment<SensorsListState, SensorsListViewModel>(),
    SensorAdapter.SensorItemClickCallback {

    private var adapter: SensorAdapter? = null

    private val viewModel: SensorsListViewModel by viewModels()

    override val baseViewModel: BuilderViewModel<SensorsListState>
        get() = viewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sensors_list, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val originIsMap: Boolean = arguments?.getBoolean(BundleKeys.startFromMap) == true

        loge("originIsMap" + originIsMap)

        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if(originIsMap) {
                    getFindViewController()?.navigateUp()
                    findNavController().navigate(R.id.sensorsListFragmentActionPopBackToMapFragment)
                } else {
                    findNavController().navigate(R.id.sensorsListFragmentActionPopBackToComponentFragment)
                }
            }
        })


        backButton.setOnClickListener {
            if(originIsMap) {
                getFindViewController()?.navigateUp()
                findNavController().navigate(R.id.sensorsListFragmentActionPopBackToMapFragment)
            } else {
                findNavController().navigate(R.id.sensorsListFragmentActionPopBackToComponentFragment)
            }

        }

        val manager = LinearLayoutManager(context)

        adapter = SensorAdapter(this)
        adapter?.let {
            sensorsRecycleView.adapter = it
            sensorsRecycleView.layoutManager = manager
        }


    }
    override fun onResume() {
        super.onResume()
        getSensorsFromDataBase()
        //viewModel.getSensors()
    }


    private fun getSensorsFromDataBase() {
        viewModel.state.value = SensorsListState.LOADING
        viewModel.getComponentWithSensors().observe(viewLifecycleOwner) {
            if (it.isNullOrEmpty()) {
                viewModel.state.value = SensorsListState.RETRY
            } else {
                viewModel.state.value = SensorsListState.IDLE
                it?.let { data ->
                    data.let { list ->

                        val sensorList = list.first().sensors
                        if (sensorList.isNullOrEmpty()) {
                            viewModel.state.value = SensorsListState.RETRY
                        } else {
                            val arrayList  = ArrayList<ProviderRecycleViewViewRowEntity>()

                            sensorList.forEach{ sensorEntity ->
                                arrayList.add(
                                    ProviderRecycleViewViewRowEntity(
                                        title = sensorEntity.sensor!!,
                                        authorizationToken = "",
                                        enable = sensorEntity.enable
                                    )
                                )
                            }

                            adapter?.let {
                                it.clear()
                                Log.e("", list.toString())
                                it.addAllData(arrayList)
                            }

                        }

                    }
                }
            }

        }
    }


    private fun getSensorsFromDataBase1() {
        viewModel.state.value = SensorsListState.LOADING
        viewModel.getSensorsFromDataBase().observe(viewLifecycleOwner) {
            if (it.isNullOrEmpty()) {
                viewModel.state.value = SensorsListState.RETRY
            } else {
                viewModel.state.value = SensorsListState.IDLE
                it?.let { data ->
                    data.let { list ->

                        val arrayList  = ArrayList<ProviderRecycleViewViewRowEntity>()

                        it.forEach{ sensorEntity ->
                            arrayList.add(
                                ProviderRecycleViewViewRowEntity(
                                    title = sensorEntity.sensor!!,
                                    authorizationToken = "",
                                    enable = sensorEntity.enable
                                )
                            )
                        }
                        adapter?.let {
                            it.clear()
                            Log.e("", list.toString())
                            it.addAllData(arrayList)
                        }
                    }
                }
            }

        }
    }

    override fun onStateChange(state: SensorsListState) {
        when (state) {
            SensorsListState.IDLE -> {
                sensorsRecycleView.visibility = View.VISIBLE
                sensorLoading.visibility = View.GONE
                sensorRecycleEmptyView.visibility = View.GONE
            }
            SensorsListState.LOADING -> {
                sensorsRecycleView.visibility = View.GONE
                sensorLoading.visibility = View.VISIBLE
                sensorRecycleEmptyView.visibility = View.GONE

            }
            SensorsListState.RETRY -> {
                sensorsRecycleView.visibility = View.GONE
                sensorLoading.visibility = View.GONE
                sensorRecycleEmptyView.visibility = View.VISIBLE
            }

            SensorsListState.START_SENSOR_DETAILS -> {
                loge(" START_SENSOR_LIST")

                sensorsRecycleView.visibility = View.VISIBLE
                sensorLoading.visibility = View.GONE
                sensorRecycleEmptyView.visibility = View.GONE
                getFindViewController()?.navigate(R.id.navigateToSensorDetailsFragment)
            }

        }
    }

    override fun onClicked(item: ProviderRecycleViewViewRowEntity) {
        loge("item " + item.title)
        viewModel.setLastSensorId(item.title)
        viewModel.navigateToSensorsOfProvider()
    }
}
