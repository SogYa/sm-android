package ru.sogya.projects.smartrevolutionapp.dialogs

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import androidx.fragment.app.DialogFragment
import com.sogya.domain.utils.Constants.STATE_ID
import ru.sogya.projects.smartrevolutionapp.R

class DeleteItemDialogFragment(
    private val dialogFragmentListener: DialogFragmentListener
) : DialogFragment() {
    interface DialogFragmentListener {
        fun positiveButtonClicked(stateId: String)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (dialog != null && dialog?.window != null)
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)

        return inflater.inflate(R.layout.dialog_delete_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val positiveButton: Button = view.findViewById(R.id.positive)
        val negativeButton: Button = view.findViewById(R.id.negative)
        positiveButton.setOnClickListener {
            val entityId = arguments?.getString(STATE_ID)
            dialogFragmentListener.positiveButtonClicked(entityId.toString())
            dismiss()
        }
        negativeButton.setOnClickListener {
            dismiss()
        }
    }
}