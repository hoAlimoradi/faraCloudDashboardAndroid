package net.faracloud.dashboard.features.setting.presentation

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
class SettingFragment : BuilderFragment<SettingState, SettingViewModel>() {

    private val viewModel: SettingViewModel by viewModels()

    override val baseViewModel: BuilderViewModel<SettingState>
        get() = viewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentValueTextView.text = viewModel.getBaseUrl()

        applyConstraintLayout.setOnClickListener {

            val baseUrl: String = baseUrlEditText.text.toString()

            if(baseUrl.trim().isNotEmpty()) {
                viewModel.setBaseUrl(baseUrl.trim())
                Toast.makeText(it.context, "successfully changed ", Toast.LENGTH_SHORT).show()
                activity?.onBackPressed()
            }else{
                Toast.makeText(it.context, "Please enter baseUrl  ! ", Toast.LENGTH_SHORT).show()
            }

        }

    }
    override fun onResume() {
        super.onResume()

    }

    override fun onStateChange(state: SettingState) {
        when (state) {
            SettingState.IDLE -> {
                loge("IDLE")
            }
            SettingState.LOADING -> {
                loge("LOADING")

            }


        }
    }


}
