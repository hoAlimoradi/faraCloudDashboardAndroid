package net.faracloud.dashboard.features.search

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class SearchViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return SearchListFragment.newInstance(SearchListType.ALL)
            1 -> return SearchListFragment.newInstance(SearchListType.PROVIDER)
            2 -> return SearchListFragment.newInstance(SearchListType.COMPONENT)
            3 -> return SearchListFragment.newInstance(SearchListType.SENSOR)
        }
        return Fragment()
    }
}

