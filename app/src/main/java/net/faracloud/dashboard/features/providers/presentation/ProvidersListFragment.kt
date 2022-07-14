package net.faracloud.dashboard.features.providers.presentation

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_add_provider.*
import kotlinx.android.synthetic.main.fragment_providers_list.*
import kotlinx.android.synthetic.main.fragment_providers_list.backButton
import net.faracloud.dashboard.R
import net.faracloud.dashboard.core.BuilderFragment
import net.faracloud.dashboard.core.BuilderViewModel
import net.faracloud.dashboard.extentions.loge
import net.faracloud.dashboard.features.BundleKeys

@AndroidEntryPoint
class ProvidersListFragment : BuilderFragment<ProviderState, ProviderViewModel>(),
    ProviderAdapter.ProviderItemClickCallback,
    DeleteProviderListener {
    private var deleteProviderDialog: DeleteProviderDialog? = null
    private var adapter: ProviderAdapter? = null
    private val viewModel: ProviderViewModel by viewModels()

    val listTenant = ArrayList<String>()
    override val baseViewModel: BuilderViewModel<ProviderState>
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
        backButton.setOnClickListener {
            findNavController().navigate(R.id.providerListFragmentActionPopBack)
        }
        //
        statisticImageButton.setOnClickListener {
            getFindViewController()?.navigate(R.id.navigateToStatisticsFragment)
        }

        addProvider.setOnClickListener {
            getFindViewController()?.navigate(R.id.navigateToAddProviderFragment)
        }

        try {
            if (!listTenant.isNullOrEmpty() ) {
                tenantSpinner.onItemSelectedListener = object :
                    AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>,
                                                view: View, position: Int, id: Long) {
                        if (!listTenant.isNullOrEmpty()) {
                            viewModel.setLastTenantName(listTenant.get(position))
                            getTenantWithProviders()
                        }
                    }
                    override fun onNothingSelected(parent: AdapterView<*>) {

                    }
                }
            }
        } catch (e: Exception) {

        }
        //observeTenants()
    }

    override fun onResume() {
        super.onResume()
        observeTenants()
    }

    private fun observeTenants() {
        viewModel.getTenants().observe(viewLifecycleOwner) {
            it?.let { data ->
                listTenant.clear()
                data.forEach {
                    listTenant.add(it.tenantName.toString())
                }
                if(!listTenant.isNullOrEmpty()) {
                    viewModel.setLastTenantName(listTenant.first())
                    getTenantWithProviders()

                    activity?.let {
                        val adapter = ArrayAdapter(it,
                            android.R.layout.simple_spinner_item, listTenant)
                        tenantSpinner.adapter = adapter
                    }
                } else {
                    viewModel.state.value = ProviderState.EMPTY
                }
            }
        }
    }

    private fun getTenantWithProviders() {
        viewModel.state.value = ProviderState.LOADING
        viewModel.getTenantWithProviders().observe(viewLifecycleOwner) {
            it?.let { data ->
                loge("data.size " + data.size)
                data.let { list ->
                    if (list.isNullOrEmpty()) {
                        viewModel.state.value = ProviderState.EMPTY
                    } else {

                        val providers = list.first().providers
                        if(!providers.isNullOrEmpty()) {
                            viewModel.state.value = ProviderState.IDLE
                            viewModel.setLastAuthorizationToken(providers.first().authorizationToken)

                            val providerRecycleViewViewRowEntityArrayList =
                                ArrayList<ProviderRecycleViewViewRowEntity>()
                            providers.forEach {
                                Log.e(" it.title ", it.providerId)
                                providerRecycleViewViewRowEntityArrayList.add(
                                    ProviderRecycleViewViewRowEntity(
                                        title = it.providerId,
                                        authorizationToken = it.authorizationToken,
                                        enable = it.enable
                                    )
                                )
                            }
                            val manager = LinearLayoutManager(context)
                            adapter = ProviderAdapter(this)
                            adapter?.let {
                                providerRecycleView.adapter = it
                                providerRecycleView.layoutManager = manager
                            }

                            adapter?.let {
                                it.clear()
                                Log.e("", list.toString())
                                it.addAllData(providerRecycleViewViewRowEntityArrayList)
                            }

                        } else {
                            viewModel.state.value = ProviderState.EMPTY
                        }
                    }
                }
            }
        }
    }

    override fun onStateChange(state: ProviderState) {
        when (state) {
            ProviderState.IDLE -> {
                providerRecycleView.visibility = View.VISIBLE
                providerRecycleEmptyView.visibility = View.GONE
                providerLoading.visibility = View.GONE
            }
            ProviderState.LOADING -> {
                providerRecycleView.visibility = View.GONE
                providerRecycleEmptyView.visibility = View.GONE
                providerLoading.visibility = View.VISIBLE
            }
            ProviderState.EMPTY -> {
                providerRecycleView.visibility = View.GONE
                providerRecycleEmptyView.visibility = View.VISIBLE
                providerLoading.visibility = View.GONE
            }
            ProviderState.EDIT_COMPONENT -> {
                getFindViewController()?.navigate(R.id.navigateToEditProviderFragment)
            }
            ProviderState.START_COMPONENT_LIST -> {
                getFindViewController()?.navigate(R.id.navigateToComponentFragment)
            }
        }
    }

    override fun onClicked(
        item: ProviderRecycleViewViewRowEntity,
        type: ProviderAdapter.ProviderItemClickCallbackType
    ) {
        loge("ddd item " + item.title)
        viewModel.setLastProviderId(item.title)
        viewModel.setLastAuthorizationToken(item.authorizationToken)
        when (type) {

            ProviderAdapter.ProviderItemClickCallbackType.MORE -> {
                viewModel.navigateToSensorsOfProvider()
            }

            ProviderAdapter.ProviderItemClickCallbackType.EDIT -> {

                loge("ddd ProviderAdapter " + viewModel.getLastProviderId())
                viewModel.navigateToEditProvider()
            }

            ProviderAdapter.ProviderItemClickCallbackType.DELETE -> {
                showDeleteProviderConfirmDialog()

            }
        }

    }

    fun showDeleteProviderConfirmDialog() {
        if (deleteProviderDialog == null)
            deleteProviderDialog = DeleteProviderDialog()

        deleteProviderDialog?.apply {
            if (!isShowing()) {
                setName(viewModel.getLastProviderId())
                setDeleteProviderListener(this@ProvidersListFragment)
                show(this@ProvidersListFragment.requireFragmentManager(), "errorTag")
            }
            onDismiss = {
                if (!isShowing())
                    deleteProviderDialog = null
            }
        }

    }

    override fun onDelete(name: String) {
        viewModel.getProviderByProviderId(name)
            .observe(viewLifecycleOwner) {
            it?.let {
                loge("onDelete getProviderByProviderId " + it.providerId)
                viewModel.deleteProvider(it)
                //viewModel.deleteComponentByProviderId(name)
                //viewModel.deleteSensorByProviderId(name)
            }
        }
        getTenantWithProviders()
    }
}
