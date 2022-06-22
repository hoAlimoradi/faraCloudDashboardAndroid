package net.faracloud.dashboard.features.tenant.presentation

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_tenants.*
import net.faracloud.dashboard.R
import net.faracloud.dashboard.core.BuilderFragment
import net.faracloud.dashboard.core.BuilderViewModel
import net.faracloud.dashboard.core.database.TenantEntity
import net.faracloud.dashboard.extentions.loge

@AndroidEntryPoint
class TenantFragment : BuilderFragment<TenantState, TenantViewModel>(),
    TenantAdapter.TenantItemClickCallback {

    private var adapter: TenantAdapter? = null
    private val viewModel: TenantViewModel by viewModels()

    var providerId: String? = null
    var authorizationToken: String? = null


    override val baseViewModel: BuilderViewModel<TenantState>
        get() = viewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tenants, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val manager = LinearLayoutManager(context)

        saveConstraintLayout.setOnClickListener {
            val nameValue: String = name.text.toString()
            if (nameValue.trim().isNotEmpty()) {
                viewModel.insertTenant(nameValue)
                Toast.makeText(it.context, "successfully added ", Toast.LENGTH_SHORT).show()
                activity?.onBackPressed()
            } else {
                Toast.makeText(it.context, "Please enter name ! ", Toast.LENGTH_SHORT).show()
            }
        }
        adapter = TenantAdapter(this)
        adapter?.let {
            tenantsRecycleView.adapter = it
            tenantsRecycleView.layoutManager = manager
        }


    }
    override fun onResume() {
        super.onResume()
        getTenantsFromDataBase()
        //viewModel.getTenants()
    }


    private fun getTenantsFromDataBase() {
        viewModel.getTenants().observe(viewLifecycleOwner) {
            it?.let { data ->
                data.let { list ->
                    val arrayList  = ArrayList<TenantEntity>()

                    list.forEach{ it ->
                        arrayList.add( it
                        )
                    }
                    adapter?.let {
                        it.clear()
                        Log.e("", list.toString())
                        it.addAllData(arrayList)
                    }
                }
            }
        }
    }






    override fun onStateChange(state: TenantState) {
        when (state) {
            TenantState.IDLE -> {
                tenantsRecycleView.visibility = View.VISIBLE
                tenantLoading.visibility = View.GONE
                tenantRecycleEmptyView.visibility = View.GONE
            }
            TenantState.LOADING -> {
                tenantsRecycleView.visibility = View.GONE
                tenantLoading.visibility = View.VISIBLE
                tenantRecycleEmptyView.visibility = View.GONE

            }
            TenantState.RETRY -> {
                tenantsRecycleView.visibility = View.GONE
                tenantLoading.visibility = View.GONE
                tenantRecycleEmptyView.visibility = View.VISIBLE
            }


        }
    }

    override fun onClicked(item: TenantEntity, type: TenantAdapter.TenantItemClickCallbackType) {

    }


}
