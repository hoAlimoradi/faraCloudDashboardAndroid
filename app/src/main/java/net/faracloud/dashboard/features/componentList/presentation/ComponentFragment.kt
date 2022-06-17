package net.faracloud.dashboard.features.componentList.presentation
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_component.*
import kotlinx.coroutines.launch
import net.faracloud.dashboard.R
import net.faracloud.dashboard.core.BuilderFragment
import net.faracloud.dashboard.core.BuilderViewModel
import net.faracloud.dashboard.extentions.loge
import net.faracloud.dashboard.features.BundleKeys
import net.faracloud.dashboard.features.providers.presentation.ProviderRecycleViewViewRowEntity

@AndroidEntryPoint
class ComponentFragment : BuilderFragment<ComponentState, ComponentViewModel>(),
    ComponentAdapter.ComponentItemClickCallback {

    private var adapter: ComponentAdapter? = null
    private val viewModel: ComponentViewModel by viewModels()

    var providerId: String = ""
    var authorizationToken: String = ""

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

        arguments?.getString(BundleKeys.providerId)?.let {
            providerId = it
        }
        arguments?.getString(BundleKeys.authorizationToken)?.let {
            authorizationToken = it
        }

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
                viewModel.getComponentsFromApi(
                    providerId = providerId,
                    authorizationToken = authorizationToken
                )
                /*providerId?.let { providerId ->
                    authorizationToken?.let { authorizationToken ->

                    }
                }*/
            }
        }
        observeComponents()

    }
    override fun onResume() {
        super.onResume()
        getComponentsFromDataBase()

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
        viewModel.state.value = ComponentState.LOADING
        viewModel.getComponentsFromDataBase().observe(viewLifecycleOwner) {
            it?.let { data ->
                data.let { list ->

                    if (list.isNullOrEmpty()) {
                        viewModel.state.value = ComponentState.EMPTY
                    } else {
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
                        val manager = LinearLayoutManager(context)
                        adapter = ComponentAdapter(this)
                        adapter?.let {
                            componentRecycleView.adapter = it
                            componentRecycleView.layoutManager = manager
                        }
                        viewModel.state.value = ComponentState.IDLE
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
                componentRecycleErrorView.visibility = View.GONE
            }
            ComponentState.LOADING -> {
                componentRecycleView.visibility = View.GONE
                componentLoading.visibility = View.VISIBLE
                componentRecycleEmptyView.visibility = View.GONE
                componentRecycleErrorView.visibility = View.GONE

            }
            ComponentState.EMPTY -> {
                componentRecycleView.visibility = View.GONE
                componentLoading.visibility = View.GONE
                componentRecycleEmptyView.visibility = View.VISIBLE
                componentRecycleErrorView.visibility = View.GONE
            }

            ComponentState.RETRY -> {
                componentRecycleView.visibility = View.GONE
                componentLoading.visibility = View.GONE
                componentRecycleEmptyView.visibility = View.GONE
                componentRecycleErrorView.visibility = View.VISIBLE
            }


            ComponentState.START_SENSOR_LIST -> {
                loge(" START_component_LIST")
                componentRecycleView.visibility = View.VISIBLE
                componentLoading.visibility = View.GONE
                componentRecycleEmptyView.visibility = View.GONE
                componentRecycleErrorView.visibility = View.GONE
                val bundle = Bundle()
                bundle.putBoolean(BundleKeys.startFromMap,false)
                getFindViewController()?.navigate(R.id.navigateToSensorListFragment, bundle)
            }

        }
    }

    override fun onClicked(item: ProviderRecycleViewViewRowEntity) {
        loge("item " + item.title)
        viewModel.navigateToSensorListFragment()
    }
}
