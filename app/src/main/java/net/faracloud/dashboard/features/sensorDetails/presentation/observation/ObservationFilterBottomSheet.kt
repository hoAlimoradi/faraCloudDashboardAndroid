package net.faracloud.dashboard.features.sensorDetails.presentation.observation

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
import kotlinx.coroutines.launch
import net.faracloud.dashboard.R
import net.faracloud.dashboard.core.BuilderBottomSheetDialogFragment
import net.faracloud.dashboard.core.BuilderViewModel
import net.faracloud.dashboard.extentions.loge
import net.faracloud.dashboard.features.sensorDetails.presentation.SensorDetailsState
import net.faracloud.dashboard.features.sensorDetails.presentation.SensorDetailsViewModel
import java.util.*

@AndroidEntryPoint
class ObservationFilterBottomSheet:
    BuilderBottomSheetDialogFragment<SensorDetailsState, SensorDetailsViewModel>() {


    private val viewModel: SensorDetailsViewModel by activityViewModels()

    override val baseViewModel: BuilderViewModel<SensorDetailsState>
        get() = viewModel

    override fun onStateChange(state: SensorDetailsState) {
        when (state) {
            SensorDetailsState.IDLE -> {
                loge("IDLE")
            }
        }
    }

    companion object {
        fun newInstance(): ObservationFilterBottomSheet =
            ObservationFilterBottomSheet().apply {
            }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(
            R.layout.observation_filter_bottom_sheet_dialog_layout,
            container,
            false
        )

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var startDate: String? = null
        var endDate: String? = null

        startDateImageButton.setOnClickListener {
            val cal = Calendar.getInstance()
            val year = cal.get(Calendar.YEAR)
            val month = cal.get(Calendar.MONTH)
            val day = cal.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog = DatePickerDialog(
                it.context,
                DatePickerDialog.OnDateSetListener { view, myear, mmonth, mdayOfMonth ->
                    startTextView.text = "" + mdayOfMonth + "/" + mmonth + "/" + myear
                    startDate = "" + mdayOfMonth + "/" + mmonth + "/" + myear+"T00:00:00"
                },
                year,
                month,
                day
            )
            datePickerDialog.show()
        }

        endDateImageButton.setOnClickListener {
            val cal = Calendar.getInstance()
            val year = cal.get(Calendar.YEAR)
            val month = cal.get(Calendar.MONTH)
            val day = cal.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog = DatePickerDialog(
                it.context,
                DatePickerDialog.OnDateSetListener { view, myear, mmonth, mdayOfMonth ->
                    endTextView.text = "" + mdayOfMonth + "/" + mmonth + "/" + myear
                    endDate = "" + mdayOfMonth + "/" + mmonth + "/" + myear+"T00:00:00"
                },
                year,
                month,
                day
            )
            datePickerDialog.show()
        }

        val step = 1
        val max = 100
        val min = 5

        categoryNumberProgressBar.max = (max - min) / step

        var categoryNumberProgressBarValue = 10

        categoryNumberProgressBar.setOnSeekBarChangeListener(
            object : OnSeekBarChangeListener {
                override fun onStopTrackingTouch(seekBar: SeekBar) {}
                override fun onStartTrackingTouch(seekBar: SeekBar) {}
                override fun onProgressChanged(
                    seekBar: SeekBar, progress: Int,
                    fromUser: Boolean
                ) {
                    categoryNumberProgressBarValue = (min + progress * step)
                    categoryNumberTextView.text = "categoryNumber: $categoryNumberProgressBarValue"
                }
            }
        )

        filterConstraintLayout.setOnClickListener {
            loge("  filterConstraintLayout clickkkkkkkkkkkkkkkkkkkkkkkk")
            viewModel.viewModelScope.launch {

                loge("  filterConstraintLayout viewModel.viewModelScope.launch")
                viewModel.getObservationsFromApi(
                    providerId = "",
                    sensor = "",
                    categoryNumber = categoryNumberProgressBarValue,
                    startDate = startDate,
                    endDate = endDate
                )
            }
            dismiss()

        }



    }


}