package net.faracloud.dashboard.features.tenant

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_providers_list.*
import net.faracloud.dashboard.R
import net.faracloud.dashboard.core.BuilderFragment
import net.faracloud.dashboard.core.BuilderViewModel
import net.faracloud.dashboard.extentions.loge
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_tenant.*
import net.faracloud.dashboard.features.sensorDetails.SensorDetailsViewPagerAdapter

@AndroidEntryPoint
class TenantFragment : BuilderFragment<TenantState, TenantViewModel>() {

    private val viewModel: TenantViewModel by viewModels()

    override val baseViewModel: BuilderViewModel<TenantState>
        get() = viewModel
    val titlesArray = arrayOf(
        "Statistics",
        "Providers"
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tenant, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        backButton.setOnClickListener {
            findNavController().navigate(R.id.tenantFragmentActionPopBack)
        }

        val adapter = TenantViewPagerAdapter(requireFragmentManager(), lifecycle)
        viewPagerTenant.adapter = adapter

        TabLayoutMediator(tabLayoutTenant, viewPagerTenant) { tab, position ->
            tab.text = titlesArray[position]
        }.attach()

    }
    override fun onResume() {
        super.onResume()

    }

    private fun setVersionText(text: String) {
        //versionCodeTextView.text = text
    }

    override fun onStateChange(state: TenantState) {
        when (state) {
            TenantState.IDLE -> {
                loge("IDLE")
            }
            TenantState.LOADING -> {
                loge("LOADING")

            }
            TenantState.RETRY -> {
                loge("RETRY")
            }

            TenantState.START_SENSOR_LIST -> {
                loge(" START_SENSOR_LIST")
                //getFindViewController()?.navigate(R.id.navigateToSensorsListFragment)
            }

        }
    }

}
