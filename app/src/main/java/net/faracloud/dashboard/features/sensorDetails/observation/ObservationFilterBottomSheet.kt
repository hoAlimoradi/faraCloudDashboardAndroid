package net.faracloud.dashboard.features.sensorDetails.observation

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageButton
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import net.faracloud.dashboard.R
import java.util.*

class ObservationFilterBottomSheet : BottomSheetDialogFragment() {

    companion object {
        fun newInstance(): ObservationFilterBottomSheet =
            ObservationFilterBottomSheet().apply {
                /*
                arguments = Bundle().apply {
                    putInt(ARG_ITEM_COUNT, itemCount)
                }
                 */
            }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
         val view = inflater.inflate(R.layout.observation_filter_bottom_sheet_dialog_layout, container, false)

        val startDateImageButton = view.findViewById<AppCompatImageButton>(R.id.startDateImageButton)
        val startTextView = view.findViewById<TextView>(R.id.startTextView)
        startDateImageButton.setOnClickListener {
            val cal = Calendar.getInstance()
            val year = cal.get(Calendar.YEAR)
            val month = cal.get(Calendar.MONTH)
            val day = cal.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog = DatePickerDialog(it.context, DatePickerDialog.OnDateSetListener { view, myear, mmonth, mdayOfMonth ->
                startTextView.text = ""+ mdayOfMonth +"/"+ mmonth +"/"+ myear
            }, year, month, day)
            datePickerDialog.show()
        }

        val endDateImageButton = view.findViewById<AppCompatImageButton>(R.id.endDateImageButton)
        val endTextView = view.findViewById<TextView>(R.id.endTextView)
        endDateImageButton.setOnClickListener {
            val cal = Calendar.getInstance()
            val year = cal.get(Calendar.YEAR)
            val month = cal.get(Calendar.MONTH)
            val day = cal.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog = DatePickerDialog(it.context, DatePickerDialog.OnDateSetListener { view, myear, mmonth, mdayOfMonth ->
                endTextView.text = ""+ mdayOfMonth +"/"+ mmonth +"/"+ myear
            }, year, month, day)
            datePickerDialog.show()
        }

         return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}