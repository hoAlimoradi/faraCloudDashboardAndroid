package net.faracloud.dashboard.features.tenant

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import net.faracloud.dashboard.features.tenant.providersList.ProvidersListFragment
import net.faracloud.dashboard.features.tenant.statistics.StatisticsFragment


class TenantViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return StatisticsFragment()
            1 -> return ProvidersListFragment()

        }
        return Fragment()
    }
}