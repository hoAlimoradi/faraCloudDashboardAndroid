package net.faracloud.dashboard.features.sensorsList

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import net.faracloud.dashboard.R
import net.faracloud.dashboard.core.BuilderFragment
import net.faracloud.dashboard.core.BuilderViewModel
import net.faracloud.dashboard.extentions.loge
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_sensors_list.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import net.faracloud.dashboard.features.providers.presentation.ProviderRecycleViewViewRowEntity
import net.faracloud.dashboard.features.statistics.StatisticsRecycleViewViewRowEntity

@AndroidEntryPoint
class SensorsListFragment : BuilderFragment<SensorsListState, SensorsListViewModel>(),
    SensorAdapter.SensorItemClickCallback {

    private var adapter: SensorAdapter? = null
    private val viewModel: SensorsListViewModel by viewModels()
    val providerId = "mobile-app@p102"

    val authorizationToken = "f1f92ad3d7488f3e7cd6a6552e26717fc5232e1ae9726d5cd16d1cbd8597cfdb"

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

//        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
//            override fun handleOnBackPressed() {
//                backPressed()
//            }
//        })

        backButton.setOnClickListener {
            findNavController().navigate(R.id.sensorsListFragmentActionPopBack)
        }

        getSensorsFromDataBase()

        val manager = LinearLayoutManager(context)

        adapter = SensorAdapter(this)
        adapter?.let {
            sensorsRecycleView.adapter = it
            sensorsRecycleView.layoutManager = manager
        }


    }
    override fun onResume() {
        super.onResume()
        //viewModel.getSensors()
    }


    private fun getSensorsFromDataBase() {
        viewModel.getSensorsFromDataBase().observe(viewLifecycleOwner) {
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
        viewModel.navigateToSensorsOfProvider()
    }
}
