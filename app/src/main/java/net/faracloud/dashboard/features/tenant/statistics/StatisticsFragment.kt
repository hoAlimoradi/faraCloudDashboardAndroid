package net.faracloud.dashboard.features.tenant.statistics

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
import kotlinx.android.synthetic.main.fragment_statistics.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import net.faracloud.dashboard.features.tenant.TenantState
import net.faracloud.dashboard.features.tenant.TenantViewModel
import net.faracloud.dashboard.features.tenant.providersList.ProviderRecycleViewViewRowEntity

//@AndroidEntryPoint
class StatisticsFragment : BuilderFragment<TenantState, TenantViewModel>()  {

    private var adapter: StatisticsAdapter? = null
    private val viewModel: TenantViewModel by activityViewModels()

    override val baseViewModel: BuilderViewModel<TenantState>
        get() = viewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_statistics, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeStatisticsStateFlow()

        val manager = LinearLayoutManager(context)

        adapter = StatisticsAdapter()
        adapter?.let {
            statisticsRecycleView.adapter = it
            statisticsRecycleView.layoutManager = manager
        }


    }
    override fun onResume() {
        super.onResume()
        //viewModel.getStatistics()
    }

    private fun observeStatisticsStateFlow() {
        // getStatisticsFromApi(name: String)

      /*  viewModel.getProviders().observe(viewLifecycleOwner) {
            it?.let { data ->
                data.let { list ->
                    list.first().run {

                    }
                }
            }
        }*/

        viewModel.viewModelScope.launch {
             viewModel.getStatisticsFromApi("mobile-app")
        }
        viewModel.viewModelScope.launch {
            viewModel.statisticsRecycleViewViewRowEntityListMutableLiveData.observe(viewLifecycleOwner) {
                it?.let { data ->
                    data.let { list ->
                        val arrayList  = ArrayList<StatisticsRecycleViewViewRowEntity>()
                        list.forEach {
                            Log.e(" it.title ", it.title)
                            arrayList.add(it)
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



    override fun onStateChange(state: TenantState) {
        when (state) {
            TenantState.IDLE -> {
                loge("IDLE")
            }
            TenantState.LOADING -> {
                loge("LOADING")

            }
            TenantState.RETRY -> {
                loge("RETRY")
            }

            TenantState.START_SENSOR_LIST -> {
                loge(" START_SENSOR_LIST")

            }

        }
    }
 
}
