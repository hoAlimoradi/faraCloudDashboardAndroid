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
import net.faracloud.dashboard.features.providers.providersList.AddNewProviderListener
import net.faracloud.dashboard.features.providers.providersList.AddProviderDialog

@AndroidEntryPoint
class ProvidersListFragment : BuilderFragment<ProviderState, ProviderViewModel>(),
    ProviderAdapter.ProviderItemClickCallback,
    AddNewProviderListener {
    private var addProviderDialog: AddProviderDialog? = null
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
            showAddProvider()
            Log.e("","ttttttttttttttttttttttttttt")
        }

    }
    override fun onResume() {
        super.onResume()
        viewModel.getProviders()
    }

    private fun observeProviders() {
        viewModel.getProviders().observe(viewLifecycleOwner) {
            it?.let { data ->
                data?.let { list ->
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


    private fun setVersionText(text: String) {
        //versionCodeTextView.text = text
    }



    override fun onStateChange(state: ProviderState) {
        when (state) {
            ProviderState.IDLE -> {
                loge("IDLE")
            }
            ProviderState.LOADING -> {
                loge("LOADING")

            }
            ProviderState.RETRY -> {
                loge("RETRY")
            }

            ProviderState.START_COMPONENT_LIST -> {
                loge(" START_COMPONENT_LIST")
                //getFindViewController()?.navigateUp()
                getFindViewController()?.navigate(R.id.navigateToComponentFragment)
            }

        }
    }

    override fun onClicked(item: ProviderRecycleViewViewRowEntity) {
        loge("item " + item.title)
        viewModel.navigateToSensorsOfProvider()

    }

    fun showAddProvider() {
        if (addProviderDialog == null)
            addProviderDialog = AddProviderDialog()
        addProviderDialog?.apply {
            if (!isShowing()) {
                //setMessage(error)
                setAddNewProviderListener(this@ProvidersListFragment)
                show(this@ProvidersListFragment.requireFragmentManager(), "errorTag")
            }
            onDismiss = {
                if (!isShowing())
                    addProviderDialog = null
            }
        }
    }

    override fun onSave(name: String, token: String) {
        loge("name " + name)
        loge("token " + token)
        viewModel.insertProvider(name, token)
        /*viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.savedProviderEvent.collect { event ->
                when (event) {
                    is SavedNewsViewModel.SavedArticleEvent.ShowUndoDeleteArticleMessage -> {
                        Snackbar.make(requireView(), "Article Deleted!", Snackbar.LENGTH_LONG)
                            .setAction("UNDO"){
                                viewModel.onUndoDeleteClick(event.article)
                            }.show()
                    }
                }
            }
        }*/
        observeProviders()
    }
}
