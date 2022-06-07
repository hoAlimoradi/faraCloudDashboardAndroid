package net.faracloud.dashboard.features.search

import net.faracloud.dashboard.features.setting.SettingState
import net.faracloud.dashboard.features.setting.SettingViewModel
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_setting.*
import net.faracloud.dashboard.R
import net.faracloud.dashboard.core.BuilderFragment
import net.faracloud.dashboard.core.BuilderViewModel
import net.faracloud.dashboard.extentions.loge

@AndroidEntryPoint
class SearchFragment :BuilderFragment<SearchState, SearchViewModel>() {

    private val viewModel: SearchViewModel by viewModels()

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

        applyConstraintLayout.setOnClickListener {

            val baseUrl: String = baseUrlEditText.text.toString()

            if(baseUrl.trim().isNotEmpty()) {
                //viewModel.insertProvider(nameValue, tokenValue)
                Toast.makeText(it.context, "successfully changed ", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(it.context, "Please enter baseUrl  ! ", Toast.LENGTH_SHORT).show()
            }

        }

    }
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