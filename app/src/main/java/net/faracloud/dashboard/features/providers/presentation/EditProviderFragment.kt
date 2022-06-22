package net.faracloud.dashboard.features.providers.presentation


import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_edit_provider.*
import kotlinx.android.synthetic.main.fragment_providers_list.backButton
import net.faracloud.dashboard.R
import net.faracloud.dashboard.core.BuilderFragment
import net.faracloud.dashboard.core.BuilderViewModel
import net.faracloud.dashboard.extentions.loge
import net.faracloud.dashboard.features.BundleKeys
import java.util.*

@AndroidEntryPoint
class EditProviderFragment : BuilderFragment<ProviderState, ProviderViewModel>() {

    var providerTableId: Int = 0
    private val viewModel: ProviderViewModel by viewModels()

    override val baseViewModel: BuilderViewModel<ProviderState>
        get() = viewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_edit_provider, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getInt(BundleKeys.id)?.let {
            providerTableId = it
        }
        arguments?.getString(BundleKeys.providerId)?.let {
            name.setText(it)
        }

        arguments?.getString(BundleKeys.authorizationToken)?.let {
            token.setText(it)
        }

        backButton.setOnClickListener {
            findNavController().navigate(R.id.editProviderFragmentActionPopBack )
        }

        saveConstraintLayout.setOnClickListener {
            val nameValue: String = name.text.toString()
            val tokenValue: String = token.text.toString()

            /*
            if(nameValue.trim().isNotEmpty()) {
                if(tokenValue.trim().isNotEmpty()) {
                    viewModel.getProviderById(providerTableId).observe(viewLifecycleOwner) {
                        loge("nameValue : " + nameValue)
                        val provider = it
                        provider.providerId = nameValue
                        provider.authorizationToken = tokenValue
                        provider.lastUpdateDevice = Date().toString()
                        viewModel.updateProvider(provider)

                    }
                    Toast.makeText(it.context, "successfully added ", Toast.LENGTH_SHORT).show()

                }else{
                    Toast.makeText(it.context, "Please enter token  ! ", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(it.context, "Please enter name ! ", Toast.LENGTH_SHORT).show()
            }

             */

        }

    }
    override fun onResume() {
        super.onResume()
        viewModel.getProviders()
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


}
