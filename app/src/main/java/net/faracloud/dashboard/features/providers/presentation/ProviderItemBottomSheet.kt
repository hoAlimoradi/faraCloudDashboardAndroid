package net.faracloud.dashboard.features.providers.presentation

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.observation_filter_bottom_sheet_dialog_layout.*
import kotlinx.android.synthetic.main.prrovider_item_bottom_sheet_dialog_layout.*
import kotlinx.coroutines.launch
import net.faracloud.dashboard.R
import net.faracloud.dashboard.core.BuilderBottomSheetDialogFragment
import net.faracloud.dashboard.core.BuilderViewModel
import net.faracloud.dashboard.extentions.loge
import java.util.*


@AndroidEntryPoint
class ProviderItemBottomSheet:
    BuilderBottomSheetDialogFragment<ProviderState, ProviderViewModel>() {

    private val viewModel: ProviderViewModel by activityViewModels()

    override val baseViewModel: BuilderViewModel<ProviderState>
        get() = viewModel

    override fun onStateChange(state: ProviderState) {
        when (state) {
            ProviderState.IDLE -> {
                loge("IDLE")
            }

        }
    }

    companion object {
        fun newInstance(): ProviderItemBottomSheet =
            ProviderItemBottomSheet().apply {
            }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(
            R.layout.prrovider_item_bottom_sheet_dialog_layout,
            container,
            false
        )

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        deleteConstraintLayout.setOnClickListener {
            viewModel.deleteProvider()
            dismiss()
        }

        editConstraintLayout.setOnClickListener {
            viewModel.navigateToEditProvider()
            dismiss()
        }

        showComponentTextView.setOnClickListener {
            viewModel.navigateToSensorsOfProvider()
            dismiss()
        }

    }


}
 