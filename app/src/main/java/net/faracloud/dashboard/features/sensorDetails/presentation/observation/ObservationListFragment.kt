package net.faracloud.dashboard.features.sensorDetails.presentation.observation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import net.faracloud.dashboard.R

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_sensor_charts.*
import net.faracloud.dashboard.core.BuilderFragment
import net.faracloud.dashboard.core.BuilderViewModel
import kotlinx.android.synthetic.main.fragment_sensor_observations.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import net.faracloud.dashboard.features.sensorDetails.presentation.SensorDetailsState
import net.faracloud.dashboard.features.sensorDetails.presentation.SensorDetailsViewModel
import net.faracloud.dashboard.features.sensorDetails.presentation.chart.ChartsAdapter

//@AndroidEntryPoint
class ObservationListFragment : BuilderFragment<SensorDetailsState, SensorDetailsViewModel>() {

    private var adapter: ObservationAdapter? = null

    private val viewModel: SensorDetailsViewModel by activityViewModels()

    override val baseViewModel: BuilderViewModel<SensorDetailsState>
        get() = viewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sensor_observations, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeObservations()

        val manager = LinearLayoutManager(context)

        adapter = ObservationAdapter()
        adapter?.let {
            observationsRecycleView.adapter = it
            observationsRecycleView.layoutManager = manager
        }
        viewModel.viewModelScope.launch {
            viewModel.getObservationsFromApi(
                categoryNumber = 10,
                startDate = null,
                endDate = null
            )
        }

    }

    override fun onResume() {
        super.onResume()
        observeObservations()

    }


    private fun observeObservations() {
        viewModel.viewModelScope.launch {
            viewModel.observationRecycleViewRowEntityListMutableLiveData
                .catch { e ->
                    viewModel.state.value =  SensorDetailsState.EMPTY
                }
                .flowOn(Dispatchers.Default)
                .collect {
                    if (it.isNullOrEmpty()) {
                        viewModel.state.value =  SensorDetailsState.EMPTY
                    } else {
                        viewModel.state.value =  SensorDetailsState.IDLE
                    }
                    it?.let { data ->
                        data?.let { list ->
                            val observationsRecycleViewViewRowEntityArrayList =
                                ArrayList<ObservationRecycleViewRowEntity>()
                            list.forEach {
                                observationsRecycleViewViewRowEntityArrayList.add(it)
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
                observationsRecycleView.visibility = View.VISIBLE
                observationsLoading.visibility = View.GONE
                observationsRecycleEmptyView.visibility = View.GONE
            }
            SensorDetailsState.LOADING -> {
                observationsRecycleView.visibility = View.GONE
                observationsLoading.visibility = View.VISIBLE
                observationsRecycleEmptyView.visibility = View.GONE

            }
            SensorDetailsState.RETRY -> {
                observationsRecycleView.visibility = View.GONE
                observationsLoading.visibility = View.GONE
                observationsRecycleEmptyView.visibility = View.VISIBLE
            }

            SensorDetailsState.EMPTY-> {
                observationsRecycleView.visibility = View.GONE
                observationsLoading.visibility = View.GONE
                observationsRecycleEmptyView.visibility = View.VISIBLE
            }
        }
    }

}
