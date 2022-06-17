package net.faracloud.dashboard.features.search

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_search_list.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.faracloud.dashboard.R
import net.faracloud.dashboard.core.BuilderFragment
import net.faracloud.dashboard.core.BuilderViewModel
import net.faracloud.dashboard.extentions.loge
import net.faracloud.dashboard.features.providers.presentation.ProviderRecycleViewViewRowEntity

class SearchListFragment : BuilderFragment<SearchState, SearchViewModel>() {

    private var adapter:  SearchListAdapter? = null

    private val viewModel: SearchViewModel by activityViewModels()

    override val baseViewModel: BuilderViewModel<SearchState>
        get() = viewModel

    var type: SearchListType = SearchListType.ALL
    companion object {
        fun newInstance(typeFragment: SearchListType): SearchListFragment =
            SearchListFragment().apply {
                type = typeFragment
            }

    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search_list, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        when(type) {
            SearchListType.ALL -> {
                observeAllList()
            }

            SearchListType.SENSOR -> {
                observeSensors()
            }

            SearchListType.COMPONENT -> {
                observeComponents()
            }

            SearchListType.PROVIDER -> {
                observeProviders()
            }
        }
         viewModel.queryTextChange.observe(viewLifecycleOwner){
            loge(" adapter?.filter?.filter(it) newText: " + it)
            adapter?.filter?.filter(it)
        }
    }
    override fun onResume() {
        super.onResume()
    }
    private fun observeAllList() {
        viewModel.viewModelScope.launch {
            val arrayList  = ArrayList<SearchRecycleViewViewRowEntity>()

            viewModel.getProviders().observe(viewLifecycleOwner) {
                it?.let { data ->
                    data.let { list ->
                        list.forEach {
                            arrayList.add(SearchRecycleViewViewRowEntity(title = it.providerId,
                                type = SearchListType.SENSOR,
                                enable = it.enable))
                        }
                    }
                }
            }
            delay(10)
            viewModel.getComponents().observe(viewLifecycleOwner) {
                it?.let { data ->
                    data.let { list ->
                        list.forEach {
                            arrayList.add(SearchRecycleViewViewRowEntity(title = it.name!!,
                                type = SearchListType.SENSOR,
                                enable = it.enable))
                        }
                    }

                }
            }
            delay(10)
            viewModel.getSensors().observe(viewLifecycleOwner) {
                it?.let { data ->
                    data.let { list ->
                        list.forEach {
                            arrayList.add(SearchRecycleViewViewRowEntity(title = it.sensor!!,
                                type = SearchListType.SENSOR,
                                enable = it.enable))
                        }
                    }
                }
                adapter = SearchListAdapter(arrayList)
                val manager = LinearLayoutManager(context)
                adapter?.let {
                    searchListRecycleView.adapter = it
                    searchListRecycleView.layoutManager = manager
                }
            }


        }
    }

    private fun observeSensors() {

        viewModel.viewModelScope.launch {
            viewModel.getSensors().observe(viewLifecycleOwner) {
                it?.let { data ->
                    data.let { list ->
                        val arrayList  = ArrayList<SearchRecycleViewViewRowEntity>()
                        list.forEach {
                            Log.e(" it.sensor ", it.sensor!!)
                            arrayList.add(SearchRecycleViewViewRowEntity(title = it.sensor!!,
                                type = SearchListType.SENSOR,
                                enable = it.enable))
                        }
                        adapter = SearchListAdapter(arrayList)
                        val manager = LinearLayoutManager(context)
                        adapter?.let {
                            searchListRecycleView.adapter = it
                            searchListRecycleView.layoutManager = manager
                        }

                    }
                }
            }
        }

    }

    private fun observeProviders() {

        viewModel.viewModelScope.launch {
            viewModel.getProviders().observe(viewLifecycleOwner) {
                it?.let { data ->
                    data.let { list ->

                        val arrayList  = ArrayList<SearchRecycleViewViewRowEntity>()
                        list.forEach {
                            Log.e(" it.sensor ", it.providerId!!)
                            arrayList.add(SearchRecycleViewViewRowEntity(title = it.providerId,
                                type = SearchListType.SENSOR,
                                enable = it.enable))
                        }

                        adapter = SearchListAdapter(arrayList)
                        val manager = LinearLayoutManager(context)
                        adapter?.let {
                            searchListRecycleView.adapter = it
                            searchListRecycleView.layoutManager = manager
                        }

                    }
                }
            }
        }
    }

    private fun observeComponents() {

        viewModel.viewModelScope.launch {
            viewModel.getComponents().observe(viewLifecycleOwner) {
                it?.let { data ->
                    data.let { list ->

                        val arrayList  = ArrayList<SearchRecycleViewViewRowEntity>()
                        list.forEach {
                            Log.e(" it. Components ", it.name!!)
                            arrayList.add(SearchRecycleViewViewRowEntity(title = it.name!!,
                                type = SearchListType.SENSOR,
                                enable = it.enable))
                        }

                        adapter = SearchListAdapter(arrayList)
                        val manager = LinearLayoutManager(context)
                        adapter?.let {
                            searchListRecycleView.adapter = it
                            searchListRecycleView.layoutManager = manager
                        }

                    }
                }
            }
        }
    }
    override fun onStateChange(state: SearchState) {
        when (state) {

            SearchState.IDLE -> {
                searchListRecycleView.visibility = View.VISIBLE
                searchListLoading.visibility = View.GONE
                searchListRecycleEmptyView.visibility = View.GONE
            }
            SearchState.LOADING -> {
                searchListRecycleView.visibility = View.GONE
                searchListLoading.visibility = View.VISIBLE
                searchListRecycleEmptyView.visibility = View.GONE

            }
            SearchState.EMPTY -> {
                searchListRecycleView.visibility = View.GONE
                searchListLoading.visibility = View.GONE
                searchListRecycleEmptyView.visibility = View.VISIBLE
            }
        }
    }

}