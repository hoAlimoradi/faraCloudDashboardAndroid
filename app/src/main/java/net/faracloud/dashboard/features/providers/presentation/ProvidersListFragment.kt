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

@AndroidEntryPoint
class ProvidersListFragment : BuilderFragment<ProviderState, ProviderViewModel>(),
    ProviderAdapter.ProviderItemClickCallback,
    DeleteProviderListener {
    private var deleteProviderDialog: DeleteProviderDialog? = null
    private var adapter: ProviderAdapter? = null
    private val viewModel: ProviderViewModel by viewModels()

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

        statisticImageButton.setOnClickListener {
            getFindViewController()?.navigate(R.id.navigateToStatisticsFragment )
        }

        observeProviders()

        val manager = LinearLayoutManager(context)

        adapter = ProviderAdapter(this)
        adapter?.let {
            providerRecycleView.adapter = it
            providerRecycleView.layoutManager = manager
        }

        addProvider.setOnClickListener {
            getFindViewController()?.navigate(R.id.navigateToAddProviderFragment )

            //showAddProvider()
            Log.e("","ttttttttttttttttttttttttttt")
        }

    }
    override fun onResume() {
        super.onResume()
        viewModel.getProviders()
    }

    private fun observeProviders() {
        viewModel.state.value = ProviderState.LOADING
        viewModel.getProviders().observe(viewLifecycleOwner) {
            it?.let { data ->
                data.let { list ->
                    if (list.isEmpty()) {
                        viewModel.state.value = ProviderState.EMPTY
                    }else {
                        viewModel.state.value = ProviderState.IDLE
                    }
                    val providerRecycleViewViewRowEntityArrayList  = ArrayList<ProviderRecycleViewViewRowEntity>()
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
                getFindViewController()?.navigate(R.id.navigateToEditProviderFragment)
            }
            ProviderState.START_COMPONENT_LIST -> {
                loge(" START_COMPONENT_LIST")
                //getFindViewController()?.navigateUp()
                getFindViewController()?.navigate(R.id.navigateToComponentFragment)
            }

        }
    }

    override fun onClicked(item: ProviderRecycleViewViewRowEntity, type: ProviderAdapter.ProviderItemClickCallbackType) {
        loge("item " + item.title)
        //viewModel.navigateToSensorsOfProvider()
        when(type) {

            ProviderAdapter.ProviderItemClickCallbackType.MORE -> {
                viewModel.navigateToSensorsOfProvider()
            }

            ProviderAdapter.ProviderItemClickCallbackType.EDIT -> {
                viewModel.navigateToEditProvider()
            }

            ProviderAdapter.ProviderItemClickCallbackType.DELETE -> {
                showDeleteProviderConfirmDialog()
                //todo delete
            }
        }
        /*this.activity?.let {
            val fragment = ProviderItemBottomSheet.newInstance()
            fragment.show(it.supportFragmentManager, "ProviderItemBottomSheet")
        }*/
    }

    fun showDeleteProviderConfirmDialog() {
        if (deleteProviderDialog == null)
            deleteProviderDialog = DeleteProviderDialog()
        deleteProviderDialog?.apply {
            if (!isShowing()) {
                //setMessage(error)
                setDeleteProviderListener(this@ProvidersListFragment)
                show(this@ProvidersListFragment.requireFragmentManager(), "errorTag")
            }
            onDismiss = {
                if (!isShowing())
                    deleteProviderDialog = null
            }
        }
    }

    override fun onDelete(name: String, token: String) {
        loge("name " + name)
        loge("token " + token)
        viewModel.deleteProvider(name, token)
        observeProviders()
    }
}
