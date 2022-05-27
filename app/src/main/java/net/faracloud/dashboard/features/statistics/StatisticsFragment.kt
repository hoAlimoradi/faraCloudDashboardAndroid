package net.faracloud.dashboard.features.statistics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import net.faracloud.dashboard.R
 
import android.os.Build 
import android.util.Log 
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_providers_list.*
import net.faracloud.dashboard.core.BuilderFragment
import net.faracloud.dashboard.core.BuilderViewModel
import net.faracloud.dashboard.extentions.loge
import kotlinx.android.synthetic.main.fragment_statistics.*
import kotlinx.android.synthetic.main.fragment_statistics.backButton
import kotlinx.coroutines.launch

@AndroidEntryPoint
class StatisticsFragment : BuilderFragment<StatisticsState, StatisticsViewModel>()  {

    private var adapter: StatisticsAdapter? = null
    private val viewModel: StatisticsViewModel by viewModels()

    override val baseViewModel: BuilderViewModel<StatisticsState>
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
        backButton.setOnClickListener {
            findNavController().navigate(R.id.statisticsFragmentActionPopBack)
        }

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



    override fun onStateChange(state: StatisticsState) {
        when (state) {
            StatisticsState.IDLE -> {
                statisticsRecycleView.visibility = View.VISIBLE
                statisticsLoading.visibility = View.GONE
                statisticsRecycleEmptyView.visibility = View.GONE
            }
            StatisticsState.LOADING -> {
                statisticsRecycleView.visibility = View.GONE
                statisticsLoading.visibility = View.VISIBLE
                statisticsRecycleEmptyView.visibility = View.GONE

            }
            StatisticsState.RETRY -> {
                statisticsRecycleView.visibility = View.GONE
                statisticsLoading.visibility = View.GONE
                statisticsRecycleEmptyView.visibility = View.VISIBLE
            }

        }
    }
 
}
