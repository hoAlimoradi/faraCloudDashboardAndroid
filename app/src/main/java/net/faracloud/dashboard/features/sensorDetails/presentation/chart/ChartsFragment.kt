package net.faracloud.dashboard.features.sensorDetails.presentation.chart
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import net.faracloud.dashboard.R

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import net.faracloud.dashboard.core.BuilderFragment
import net.faracloud.dashboard.core.BuilderViewModel
import kotlinx.android.synthetic.main.fragment_sensor_charts.*
import net.faracloud.dashboard.features.sensorDetails.presentation.SensorDetailsState
import net.faracloud.dashboard.features.sensorDetails.presentation.SensorDetailsViewModel

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

    }

    override fun onResume() {
        super.onResume()
        observeObservations()
    }


    private fun observeObservations() {
        viewModel.state.value =  SensorDetailsState.LOADING
        viewModel.getObservationsFromDataBase()
            .observe(viewLifecycleOwner) {
                if (it.isNullOrEmpty()) {
                    viewModel.state.value =  SensorDetailsState.EMPTY
                } else {
                    viewModel.state.value =  SensorDetailsState.IDLE
                    it?.let { data ->
                        data?.let { list ->

                            val observationsRecycleViewViewRowEntityArrayList  = ArrayList<Float>()
                            list.forEach {
                                observationsRecycleViewViewRowEntityArrayList.add(it.value.toFloat())
                            }
                            val manager = LinearLayoutManager(context)

                            adapter = ChartsAdapter(observationsRecycleViewViewRowEntityArrayList)
                            adapter?.let {
                                chartsRecycleView.adapter = it
                                chartsRecycleView.layoutManager = manager
                            }
                        }
                    }
                }


        }
    }
    override fun onStateChange(state: SensorDetailsState) {
        when (state) {
            SensorDetailsState.IDLE -> {
                chartsRecycleView.visibility = View.VISIBLE
                chartsLoading.visibility = View.GONE
                chartsRecycleEmptyView.visibility = View.GONE
            }
            SensorDetailsState.LOADING -> {
                chartsRecycleView.visibility = View.GONE
                chartsLoading.visibility = View.VISIBLE
                chartsRecycleEmptyView.visibility = View.GONE

            }
            SensorDetailsState.RETRY -> {
                chartsRecycleView.visibility = View.GONE
                chartsLoading.visibility = View.GONE
                chartsRecycleEmptyView.visibility = View.VISIBLE
            }

            SensorDetailsState.EMPTY -> {
                chartsRecycleView.visibility = View.GONE
                chartsLoading.visibility = View.GONE
                chartsRecycleEmptyView.visibility = View.VISIBLE
            }
        }
    }

}
