package ru.sogya.projects.smartrevolutionapp.dialogs

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import androidx.fragment.app.DialogFragment
import ru.sogya.projects.smartrevolutionapp.R

class LogOutDialogFragment : DialogFragment() {
    interface DialogFragmentListener {
        fun positiveButtonClicked()
    }

    private lateinit var listenner: DialogFragmentListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listenner = activity as DialogFragmentListener
        } catch (e: ClassCastException) {
            throw ClassCastException(activity.toString())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (dialog != null && dialog?.window !=null)
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)

        return inflater.inflate(R.layout.dialog_log_out, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val positiveButton: Button = view.findViewById(R.id.positive)
        val negativeButton: Button = view.findViewById(R.id.negative)
        positiveButton.setOnClickListener {
            listenner.positiveButtonClicked()
        }
        negativeButton.setOnClickListener {
            dismiss()
        }
    }
}