package net.faracloud.dashboard.features.providers.presentation

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_providers_list.*
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

    override val baseViewModel: BuilderViewModel<ProviderState>
        get() = viewModel

    var token: String? = null
    var tenant: String? = null
    var providerTableId = 0
    var providerId: String? = null
    var authorizationToken: String? = null

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

        statisticImageButton.setOnClickListener {
            val bundle = Bundle()
            bundle.putString(BundleKeys.tenant, tenant)
            getFindViewController()?.navigate(R.id.navigateToStatisticsFragment, bundle)
        }

        addProvider.setOnClickListener {
            getFindViewController()?.navigate(R.id.navigateToAddProviderFragment)
        }

    }

    override fun onResume() {
        super.onResume()
        observeProviders()
    }

    private fun observeProviders() {
        viewModel.state.value = ProviderState.LOADING
        viewModel.getProviders().observe(viewLifecycleOwner) {
            it?.let { data ->
                data.let { list ->
                    if (list.isEmpty()) {
                        viewModel.state.value = ProviderState.EMPTY
                    } else {
                        viewModel.state.value = ProviderState.IDLE
                        val strs = list.first().providerId.split("@").toTypedArray()
                        tenant = strs[0]
                        token = list.first().authorizationToken
                    }
                    val providerRecycleViewViewRowEntityArrayList =
                        ArrayList<ProviderRecycleViewViewRowEntity>()
                    list.forEach {
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
                loge("LOADING")
                providerRecycleView.visibility = View.GONE
                providerRecycleEmptyView.visibility = View.GONE
                providerLoading.visibility = View.VISIBLE
            }
            ProviderState.EMPTY -> {
                loge("LOADING")
                providerRecycleView.visibility = View.GONE
                providerRecycleEmptyView.visibility = View.VISIBLE
                providerLoading.visibility = View.GONE
            }
            ProviderState.EDIT_COMPONENT -> {
                loge("EDIT_COMPONENT")
                val bundle = Bundle()
                bundle.putInt(BundleKeys.id, providerTableId)
                bundle.putString(BundleKeys.providerId, providerId)
                bundle.putString(BundleKeys.authorizationToken, authorizationToken)
                getFindViewController()?.navigate(R.id.navigateToEditProviderFragment, bundle)
            }
            ProviderState.START_COMPONENT_LIST -> {
                loge(" START_COMPONENT_LIST")
                //getFindViewController()?.navigateUp()
                val bundle = Bundle()
                bundle.putString(BundleKeys.providerId, providerId)
                bundle.putString(BundleKeys.authorizationToken, authorizationToken)
                getFindViewController()?.navigate(R.id.navigateToComponentFragment, bundle)
            }

        }
    }

    override fun onClicked(
        item: ProviderRecycleViewViewRowEntity,
        type: ProviderAdapter.ProviderItemClickCallbackType
    ) {
        loge("item " + item.title)
        //viewModel.navigateToSensorsOfProvider()
        providerId = item.title
        authorizationToken = item.authorizationToken

        viewModel.getProviderByProviderId(item.title).observe(viewLifecycleOwner) {

            it?.let {
                providerTableId = it.id
            }

            when (type) {

                ProviderAdapter.ProviderItemClickCallbackType.MORE -> {
                    viewModel.navigateToSensorsOfProvider()

                }

                ProviderAdapter.ProviderItemClickCallbackType.EDIT -> {
                    viewModel.navigateToEditProvider()
                }

                ProviderAdapter.ProviderItemClickCallbackType.DELETE -> {
                    showDeleteProviderConfirmDialog()

                }
            }
        }

    }

    fun showDeleteProviderConfirmDialog() {
        if (deleteProviderDialog == null)
            deleteProviderDialog = DeleteProviderDialog()
        deleteProviderDialog?.apply {
            if (!isShowing()) {
                setName(providerId!!)
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
        viewModel.getProviderByProviderId(name).observe(viewLifecycleOwner) {
            it?.let {

                /*viewModel.getAllSensors().observe(viewLifecycleOwner) {
                    it?.let {

                    }
                }

                viewModel.getAllComponents().observe(viewLifecycleOwner) {
                    it?.let {

                    }
                }*/
                loge("onDelete getProviderByProviderId " + it.providerId)
                viewModel.deleteProvider(it)
            }
        }
        observeProviders()
    }

   /* fun deleteAllSensorsByComponentId(componentId: String) {

    }

    fun deleteAllComponentsByProviderId(providerId: String) {

    }*/
}
