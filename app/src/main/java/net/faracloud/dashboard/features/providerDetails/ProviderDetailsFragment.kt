package net.faracloud.dashboard.features.providerDetails

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import net.faracloud.dashboard.R

class ProviderDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = ProviderDetailsFragment()
    }

    private lateinit var viewModel: ProviderDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_provider_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProviderDetailsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}