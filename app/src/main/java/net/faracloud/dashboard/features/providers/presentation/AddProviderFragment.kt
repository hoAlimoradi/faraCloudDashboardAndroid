package net.faracloud.dashboard.features.providers.presentation

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import com.google.zxing.integration.android.IntentIntegrator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_add_provider.*
import net.faracloud.dashboard.R
import net.faracloud.dashboard.core.BuilderFragment
import net.faracloud.dashboard.core.BuilderViewModel
import net.faracloud.dashboard.extentions.loge
import org.json.JSONException
import org.json.JSONObject

@AndroidEntryPoint
class AddProviderFragment : BuilderFragment<ProviderState, ProviderViewModel>() {

    private val viewModel: ProviderViewModel by viewModels()
    var tenantValue: String? = null
    val listTenant = ArrayList<String>()
    override val baseViewModel: BuilderViewModel<ProviderState>
        get() = viewModel

    private var qrScan: IntentIntegrator? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_provider, container, false)
    }


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Initialize the Scan Object
        /*qrScan = IntentIntegrator(activity)

        qrCodeImageView.setOnClickListener {
            qrScan?.initiateScan()
        }*/

        addBusinessImageView.setOnClickListener {
            getFindViewController()?.navigate(R.id.navigateToAddTenantFragment)
        }

        spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        view: View, position: Int, id: Long) {
                tenantValue =  listTenant.get(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }


        saveConstraintLayout.setOnClickListener {

            loge("name " + name)
            loge("token " + token)
            val nameValue: String = name.text.toString()
            val tokenValue: String = token.text.toString()

            if (!tenantValue.isNullOrEmpty()) {
                tenantValue?.let { tenantValue->
                    if (nameValue.trim().isNotEmpty()) {
                        if (tokenValue.trim().isNotEmpty()) {
                            viewModel.insertProvider(tenantValue, nameValue, tokenValue)
                            Toast.makeText(it.context, "successfully added ", Toast.LENGTH_SHORT).show()
                            activity?.onBackPressed()
                        } else {
                            Toast.makeText(it.context, "Please enter token  ! ", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(it.context, "Please enter name ! ", Toast.LENGTH_SHORT).show()
                    }
                }

            } else {
                Toast.makeText(it.context, "Please select a tenant  ! ", Toast.LENGTH_SHORT).show()
            }


        }

    }

    override fun onResume() {
        super.onResume()
        viewModel.getProviders()
        getTenants()
    }

    private fun getTenants() {
        viewModel.getTenants().observe(viewLifecycleOwner) {
            it?.let { data ->
               listTenant.clear()
                data.forEach {
                    listTenant.add(it.tenantName.toString())
                }
                if(!listTenant.isNullOrEmpty()) {
                    tenantValue = listTenant.first()
                    activity?.let {
                        val adapter = ArrayAdapter(it,
                            android.R.layout.simple_spinner_item, listTenant)
                        spinner.adapter = adapter
                    }
                } else {
                    viewModel.state.value = ProviderState.EMPTY
                }

            }
        }
    }

    override fun onStateChange(state: ProviderState) {
        when (state) {
            ProviderState.IDLE -> {
                loge("IDLE")
            }
            ProviderState.LOADING -> {
                loge("LOADING")

            }

            ProviderState.START_COMPONENT_LIST -> {
                loge(" START_COMPONENT_LIST")
                //getFindViewController()?.navigateUp()
                getFindViewController()?.navigate(R.id.navigateToComponentFragment)
            }

        }
    }


    //Getting the scan results
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {

            //Check to see if QR Code has nothing in it
            if (result.contents == null) {
                Toast.makeText(this.context, "Result Not Found", Toast.LENGTH_LONG).show()
            } else {
                //QR Code contains some data
                try {

                    //Convert the QR Code Data to JSON
                    val obj = JSONObject(result.contents)
                    //Set up the TextView Values using the data from JSON
                    Log.e("متن ", obj.toString())


                    val nameValue = obj.getString("name")
                    val tokenValue = obj.getString("token")
                    loge("nameValue " + nameValue)
                    loge("tokenValue " + tokenValue)

                    name.setText(nameValue)
                    token.setText(tokenValue)

                } catch (e: JSONException) {
                    Toast.makeText(this.context, result.contents, Toast.LENGTH_LONG).show()
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

}

