package net.faracloud.dashboard.features.providers.presentation

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

class DeleteProviderDialog : DialogFragment() {

    private lateinit var rootView: View

    private lateinit var listener: DeleteProviderListener

    var name: String? = null

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
        rootView = inflater.inflate(R.layout.dialog_add_provider, container, false)
        rootView.okConstraintLayout.setOnClickListener {
            listener.onDelete( name =  name!! )
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

    fun setMessage(error: ErrorDialogModel): DeleteProviderDialog {
        return this
    }

    fun setName(name: String): DeleteProviderDialog {
        this.name = name
        return this
    }

    fun setDeleteProviderListener(listener: DeleteProviderListener): DeleteProviderDialog {
        this.listener = listener
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

data class ErrorDialogModel(
    val error: String,
    val tryAgain: String?
)

interface DeleteProviderListener {
    fun onDelete(name: String)
}