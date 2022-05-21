package net.faracloud.dashboard.features.sensorDetails.chart
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import net.faracloud.dashboard.R

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import net.faracloud.dashboard.core.BuilderFragment
import net.faracloud.dashboard.core.BuilderViewModel
import net.faracloud.dashboard.extentions.loge
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_sensor_charts.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import net.faracloud.dashboard.features.sensorDetails.SensorDetailsState
import net.faracloud.dashboard.features.sensorDetails.SensorDetailsViewModel
import net.faracloud.dashboard.features.sensorDetails.observation.ObservationAdapter
import net.faracloud.dashboard.features.sensorDetails.observation.ObservationRecycleViewRowEntity

//@AndroidEntryPoint
class ChartsFragment : BuilderFragment<SensorDetailsState, SensorDetailsViewModel>()  {

    private var adapter: ChartsAdapter? = null

    private val viewModel: SensorDetailsViewModel by activityViewModels()

    override val baseViewModel: BuilderViewModel<SensorDetailsState>
        get() = viewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sensor_charts, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeObservationsStateFlow()

        val manager = LinearLayoutManager(context)

        adapter = ChartsAdapter()
        adapter?.let {
            chartsRecycleView.adapter = it
            chartsRecycleView.layoutManager = manager
        }
    }
    override fun onResume() {
        super.onResume()
        viewModel.getObservations()
    }

    private fun observeObservationsStateFlow() {
        viewModel.viewModelScope.launch {
            viewModel.observationRecycleViewRowEntityListMutableLiveData
                .catch { e ->

                }
                .flowOn(Dispatchers.Default)
                .collect {
                    it?.let { data ->
                        data?.let { list ->
                           // val observationsRecycleViewViewRowEntityArrayList  = ArrayList<ObservationRecycleViewRowEntity>()
                            val observationsRecycleViewViewRowEntityArrayList  = ArrayList<String>()
                            list.forEach {
                                observationsRecycleViewViewRowEntityArrayList.add(it.value)
                            }
                            adapter?.let {
                                it.clear()
                                Log.e("", list.toString())
                                it.addAllData(observationsRecycleViewViewRowEntityArrayList)
                            }
                        }
                    }
                }
        }
    }

    override fun onStateChange(state: SensorDetailsState) {
        when (state) {
            SensorDetailsState.IDLE -> {
                loge("IDLE")
            }
        }
    }

}
