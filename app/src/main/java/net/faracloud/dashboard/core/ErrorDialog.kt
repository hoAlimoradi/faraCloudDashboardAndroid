package net.faracloud.dashboard.core

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import net.faracloud.dashboard.R
import net.faracloud.dashboard.extentions.setWidthPercent

const val TAG = "errorDialog"
class ErrorDialog : DialogFragment() {

    private lateinit var rootView: View
    private var message = ""
    private var tryAgainMessageDefualtValue = ""

    var onDismiss: () -> Unit = {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        rootView = inflater.inflate(R.layout.dialog_error, container, false)
        return rootView
    }

    override fun onResume() {
        super.onResume()
        setWidthPercent(80)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       /* todo refactor based on ui
           errorTextView.text = message
           tryAgainTextView.text = tryAgainMessageDefualtValue*/
    }

    fun setMessage(error: ErrorDialogModel): ErrorDialog {

        message = error.error
        /* todo refactor based on ui
            errorTextView?.let {
            it.text = message
        }*/

        error.tryAgain?.let {
            Log.e("تسسست" , it)
        }
        return this
    }

    fun isShowing(): Boolean {
        return dialog?.isShowing ?: false
    }

    override fun dismiss() {
        super.dismiss()
        onDismiss.invoke()
    }

    override fun onDestroyView() {
        onDismiss.invoke()
        super.onDestroyView()
    }
}

data class ErrorDialogModel (
    val error: String,
    val tryAgain: String?
)
