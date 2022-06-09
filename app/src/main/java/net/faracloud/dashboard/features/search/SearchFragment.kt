package net.faracloud.dashboard.features.search

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_sensor_details.*
import kotlinx.android.synthetic.main.fragment_setting.*
import kotlinx.android.synthetic.main.fragment_setting.backButton
import net.faracloud.dashboard.R
import net.faracloud.dashboard.core.BuilderFragment
import net.faracloud.dashboard.core.BuilderViewModel
import net.faracloud.dashboard.extentions.loge


@AndroidEntryPoint
class SearchFragment :BuilderFragment<SearchState, SearchViewModel>() {

    val titles = arrayOf(
        "All",
        "Providers",
        "Components",
        "Sensors"
    )

    private val viewModel: SearchViewModel by activityViewModels()// viewModels()

    override val baseViewModel: BuilderViewModel<SearchState>
        get() = viewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        backButton.setOnClickListener {
            //findNavController().navigate(R.id.settingFragmentActionPopBack)
        }

        val searchIcon = searchBar.findViewById<ImageView>(androidx.appcompat.R.id.search_mag_icon)
        searchIcon.setColorFilter(ContextCompat.getColor(searchBar.context, R.color.purple_500))


        val cancelIcon = searchBar.findViewById<ImageView>(androidx.appcompat.R.id.search_close_btn)
        cancelIcon.setColorFilter(Color.RED)

        val textView = searchBar.findViewById<TextView>(androidx.appcompat.R.id.search_src_text)
        //textView.setTextColor(ContextCompat.getColor(searchBar.context, R.color.teal_200))

        val adapter = SearchViewPagerAdapter(requireFragmentManager(), lifecycle)
        searchViewPager.adapter = adapter

        TabLayoutMediator(searchTabLayout, searchViewPager) { tab, position ->
            tab.text = titles[position]

        }.attach()


        searchBar.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                loge(" onQueryTextSubmit query: " + query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                //loge(" onQueryTextChange newText: " + newText)
                //TODO adapter.filter.filter(newText)
                viewModel.queryTextChange.value = newText
                return false
            }

        })

    }
/*
    private fun createCustomTabView(tabText: String, tabSizeSp: Int, textColor: Int): View? {
        val tabCustomView: View = layoutInflater.inflate(R.layout.tab_customview, null)
        val tabTextView = tabCustomView.findViewById<TextView>(R.id.tabTV)
        tabTextView.text = tabText
        tabTextView.textSize = tabSizeSp.toFloat()
        tabTextView.setTextColor(ContextCompat.getColor(tabCustomView.context, textColor))
        return tabCustomView
    }

    private fun setTabTextSize(tab: TabLayout.Tab, tabSizeSp: Int, textColor: Int) {
        val tabCustomView = tab.customView
        val tabTextView = tabCustomView!!.findViewById<TextView>(R.id.tabTV)
        tabTextView.textSize = tabSizeSp.toFloat()
        tabTextView.setTextColor(ContextCompat.getColor(tabCustomView.context, textColor))
    }*/

    override fun onResume() {
        super.onResume()

    }

    override fun onStateChange(state: SearchState) {
        when (state) {
            SearchState.IDLE -> {
                loge("IDLE")
            }
            SearchState.LOADING -> {
                loge("LOADING")

            }


        }
    }


}