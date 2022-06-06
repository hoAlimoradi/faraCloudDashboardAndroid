package net.faracloud.dashboard.features.sensorDetails.information

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
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import net.faracloud.dashboard.core.BuilderFragment
import net.faracloud.dashboard.core.BuilderViewModel
import net.faracloud.dashboard.extentions.loge
import kotlinx.android.synthetic.main.fragment_sensor_infotmations.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import net.faracloud.dashboard.features.providers.presentation.ProviderRecycleViewViewRowEntity
import net.faracloud.dashboard.features.sensorDetails.SensorDetailsState
import net.faracloud.dashboard.features.sensorDetails.SensorDetailsViewModel

class InformationsFragment : BuilderFragment<SensorDetailsState, SensorDetailsViewModel>()  {
    //InformationRecycleViewRowEntity
    private var adapter: InformationAdapter? = null

    private val viewModel: SensorDetailsViewModel by activityViewModels()

    override val baseViewModel: BuilderViewModel<SensorDetailsState>
        get() = viewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sensor_infotmations, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeObservationsStateFlow()
        getSensorInformation()
        /*val manager = LinearLayoutManager(context)

        adapter = InformationAdapter()
        adapter?.let {
            informationRecycleView.adapter = it
            informationRecycleView.layoutManager = manager
        }*/
    }
    override fun onResume() {
        super.onResume()
       // viewModel.getObservations()
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

                        }
                    }
                }
        }
    }

    private fun getSensorInformation() {
        viewModel.getSensorsFromDataBase().observe(viewLifecycleOwner) {
            it?.let { data ->
                data.let { list ->
                    val item = list.first()
                    val observationsRecycleViewViewRowEntityArrayList  = ArrayList<String>()
                    observationsRecycleViewViewRowEntityArrayList.add(item.sensor.toString())
                    observationsRecycleViewViewRowEntityArrayList.add(item.dataType.toString())
                    observationsRecycleViewViewRowEntityArrayList.add(item.latitude.toString())
                    observationsRecycleViewViewRowEntityArrayList.add(item.longitude.toString())
                    val location = item.latitude.toString() + " " + item.longitude.toString()
                    observationsRecycleViewViewRowEntityArrayList.add(location)
                    observationsRecycleViewViewRowEntityArrayList.add(item.state.toString())
                    observationsRecycleViewViewRowEntityArrayList.add(item.type.toString())
                    observationsRecycleViewViewRowEntityArrayList.add(item.unit.toString())
                    observationsRecycleViewViewRowEntityArrayList.add(item.timeZone.toString())
                    observationsRecycleViewViewRowEntityArrayList.add(item.publicAccess.toString())
                    observationsRecycleViewViewRowEntityArrayList.add(item.state.toString())
                    observationsRecycleViewViewRowEntityArrayList.add(item.createdAt.toString())
                    observationsRecycleViewViewRowEntityArrayList.add(item.updatedAt.toString())

                    val manager = LinearLayoutManager(context)

                    adapter = InformationAdapter(observationsRecycleViewViewRowEntityArrayList)
                    adapter?.let {
                        informationRecycleView.adapter = it
                        informationRecycleView.layoutManager = manager
                    }

                    /*adapter?.let {
                        it.clear()
                        Log.e("", list.toString())
                        it.addAllData(observationsRecycleViewViewRowEntityArrayList)
                    }*/
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
