package net.faracloud.dashboard.features.providers.providersList

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.dialog_add_provider.view.*
import net.faracloud.dashboard.R
import net.faracloud.dashboard.extentions.setWidthPercent

const val TAG = "errorDialog"
class AddProviderDialog : DialogFragment() {

    private lateinit var rootView: View
    private lateinit var listener: AddNewProviderListener

    var onDismiss: () -> Unit = {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

   /* override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = parentFragment as AddNewProviderListener
        } catch (e: ClassCastException) {
            throw ClassCastException("$context must implement UpdateNameListener")
        }
    }*/

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        rootView = inflater.inflate(R.layout.dialog_add_provider, container, false)
        rootView.submitNewProvider.setOnClickListener {
            listener.onSave(name = rootView.name.text.toString() ,
            token = rootView.token.text.toString())
            dismiss()
        }
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

    fun setMessage(error: ErrorDialogModel): AddProviderDialog {

        return this
    }

    fun setAddNewProviderListener(listener: AddNewProviderListener): AddProviderDialog {
        this.listener =  listener
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

interface AddNewProviderListener {
    fun onSave(name: String, token: String)
}