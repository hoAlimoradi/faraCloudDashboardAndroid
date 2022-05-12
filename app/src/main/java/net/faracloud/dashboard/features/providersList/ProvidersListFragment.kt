package net.faracloud.dashboard.features.providersList

import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_map.*
import kotlinx.android.synthetic.main.fragment_providers_list.*
import net.faracloud.dashboard.R
import net.faracloud.dashboard.core.BuilderFragment
import net.faracloud.dashboard.core.BuilderViewModel
import net.faracloud.dashboard.extentions.loge
import net.faracloud.dashboard.features.map.MapState
import net.faracloud.dashboard.features.map.MapViewModel
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProvidersListFragment : BuilderFragment<ProvidersListState, ProvidersListViewModel>() {

    private var adapter: ProviderAdapter? = null
    private val viewModel: ProvidersListViewModel by viewModels()

    override val baseViewModel: BuilderViewModel<ProvidersListState>
        get() = viewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_providers_list, container, false)
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
            findNavController().navigate(R.id.providersListFragmentActionPopBack)
//            findNavController().popBackStack()
//            getFindViewController()?.navigateUp()
            //getFindViewController()?.popBackStack()

        }

        observeProvidersStateFlow()

        val manager = LinearLayoutManager(context)

        adapter = ProviderAdapter()
        adapter?.let {
            providerRecycleView.adapter = it
            providerRecycleView.layoutManager = manager
        }


    }
    override fun onResume() {
        super.onResume()
        viewModel.getProviders()
    }

    private fun observeProvidersStateFlow() {
        viewModel.viewModelScope.launch {
            viewModel.providerRecycleViewViewRowEntityListMutableLiveData
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



    override fun onStateChange(state: ProvidersListState) {
        when (state) {
            ProvidersListState.IDLE -> {
                loge("IDLE")
            }
            ProvidersListState.LOADING -> {
                loge("START_DETAIL")

            }
            ProvidersListState.RETRY -> {
                loge("FORCE_UPDATE")
            }

        }
    }
}
