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
import net.faracloud.dashboard.features.tenant.providersList.ProviderRecycleViewViewRowEntity

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

//        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
//            override fun handleOnBackPressed() {
//                backPressed()
//            }
//        })

        backButton.setOnClickListener {
            findNavController().navigate(R.id.sensorsListFragmentActionPopBack)
        }

        observeSensorsStateFlow()

        val manager = LinearLayoutManager(context)

        adapter = SensorAdapter(this)
        adapter?.let {
            sensorsRecycleView.adapter = it
            sensorsRecycleView.layoutManager = manager
        }


    }
    override fun onResume() {
        super.onResume()
        viewModel.getSensors()
    }

    private fun observeSensorsStateFlow() {
        viewModel.viewModelScope.launch {
            viewModel.sensorRecycleViewViewRowEntityListMutableLiveData
                .catch { e ->

                }
                .flowOn(Dispatchers.Default)
                .collect {
                    it?.let { data ->
                        data?.let { list ->
                            val providerRecycleViewViewRowEntityArrayList  = ArrayList<ProviderRecycleViewViewRowEntity>()
                            list.forEach {
                                Log.e(" it.title ", it.title)
                                providerRecycleViewViewRowEntityArrayList.add(it)
                            }
                            adapter?.let {
                                it.clear()
                                Log.e("", list.toString())
                                it.addAllData(providerRecycleViewViewRowEntityArrayList)
                            }
                        }
                    }
                }
        }
    }
    private fun onDataReady(data: List<ProviderRecycleViewViewRowEntity>){
        data?.let { list ->
            val providerRecycleViewViewRowEntityArrayList  = ArrayList<ProviderRecycleViewViewRowEntity>()
            list.forEach {
                Log.e(" it.title ", it.title)
                providerRecycleViewViewRowEntityArrayList.add(it)
            }
            adapter?.let {
                it.clear()
                Log.e("", list.toString())
                it.addAllData(providerRecycleViewViewRowEntityArrayList)
            }
        }
    }


    private fun setVersionText(text: String) {
        //versionCodeTextView.text = text
    }



    override fun onStateChange(state: SensorsListState) {
        when (state) {
            SensorsListState.IDLE -> {
                loge("IDLE")
            }
            SensorsListState.LOADING -> {
                loge("LOADING")

            }
            SensorsListState.RETRY -> {
                loge("RETRY")
            }

            SensorsListState.START_SENSOR_DETAILS -> {
                loge(" START_SENSOR_LIST")
                getFindViewController()?.navigate(R.id.navigateToSensorDetailsFragment)
            }

        }
    }

    override fun onClicked(item: ProviderRecycleViewViewRowEntity) {
        loge("item " + item.title)
        viewModel.navigateToSensorsOfProvider()
    }
}

/*
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

    }

}

 */