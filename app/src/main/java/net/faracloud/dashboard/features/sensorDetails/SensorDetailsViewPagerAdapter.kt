package net.faracloud.dashboard.features.sensorDetails

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import net.faracloud.dashboard.features.sensorDetails.chart.ChartsFragment
import net.faracloud.dashboard.features.sensorDetails.information.InformationsFragment
import net.faracloud.dashboard.features.sensorDetails.observation.ObservationListFragment

private const val NUM_TABS = 3

public class SensorDetailsViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return ChartsFragment()
            1 -> return InformationsFragment()
            2 -> return ObservationListFragment()
        }
        return Fragment()
    }
}