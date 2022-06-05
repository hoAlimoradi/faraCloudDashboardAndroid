package net.faracloud.dashboard.features.componentList 
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
import kotlinx.android.synthetic.main.fragment_component.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import net.faracloud.dashboard.features.providers.presentation.ProviderRecycleViewViewRowEntity
import net.faracloud.dashboard.features.statistics.StatisticsRecycleViewViewRowEntity

@AndroidEntryPoint
class ComponentFragment : BuilderFragment<ComponentState, ComponentViewModel>(),
    ComponentAdapter.ComponentItemClickCallback {

    private var adapter: ComponentAdapter? = null
    private val viewModel: ComponentViewModel by viewModels()
    val providerId = "mobile-app@p102"

    val authorizationToken = "f1f92ad3d7488f3e7cd6a6552e26717fc5232e1ae9726d5cd16d1cbd8597cfdb"

    override val baseViewModel: BuilderViewModel<ComponentState>
        get() = viewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_component, container, false)
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
            findNavController().navigate(R.id.componentFragmentActionPopBack)
        }

        refreshButton.setOnClickListener {
            viewModel.viewModelScope.launch {
                viewModel.getComponentsFromApi("")
            }
        }
        /*viewModel.viewModelScope.launch {
            viewModel.getComponentsFromDataBase()
        }*/
        getComponentsFromDataBase()
        observeComponents()

        val manager = LinearLayoutManager(context)

        adapter = ComponentAdapter(this)
        adapter?.let {
            componentRecycleView.adapter = it
            componentRecycleView.layoutManager = manager
        }


    }
    override fun onResume() {
        super.onResume()
        //viewModel.getcomponents()
    }

    private fun observeComponents() {

        viewModel.viewModelScope.launch {
            viewModel.componentRecycleViewViewRowEntityListMutableLiveData.observe(viewLifecycleOwner) {
                it?.let { data ->
                    data.let { list ->
                        val arrayList  = ArrayList<ProviderRecycleViewViewRowEntity>()
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

    private fun getComponentsFromDataBase() {
        viewModel.getComponentsFromDataBase().observe(viewLifecycleOwner) {
            it?.let { data ->
                data.let { list ->
                    val arrayList  = ArrayList<ProviderRecycleViewViewRowEntity>()

                    it.forEach{ componentEntity ->
                        arrayList.add(
                            ProviderRecycleViewViewRowEntity(
                                title = componentEntity.name!!,
                                authorizationToken = "",
                                enable = componentEntity.enable
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
    
    override fun onStateChange(state: ComponentState) {
        when (state) {
            ComponentState.IDLE -> {
                componentRecycleView.visibility = View.VISIBLE
                componentLoading.visibility = View.GONE
                componentRecycleEmptyView.visibility = View.GONE
            }
            ComponentState.LOADING -> {
                componentRecycleView.visibility = View.GONE
                componentLoading.visibility = View.VISIBLE
                componentRecycleEmptyView.visibility = View.GONE

            }
            ComponentState.RETRY -> {
                componentRecycleView.visibility = View.GONE
                componentLoading.visibility = View.GONE
                componentRecycleEmptyView.visibility = View.VISIBLE
            }

            ComponentState.START_SENSOR_LIST -> {
                loge(" START_component_LIST")
                componentRecycleView.visibility = View.VISIBLE
                componentLoading.visibility = View.GONE
                componentRecycleEmptyView.visibility = View.GONE
                getFindViewController()?.navigate(R.id.navigateToSensorListFragment)
            }

        }
    }

    override fun onClicked(item: ProviderRecycleViewViewRowEntity) {
        loge("item " + item.title)
        viewModel.navigateToSensorListFragment()
    }
}