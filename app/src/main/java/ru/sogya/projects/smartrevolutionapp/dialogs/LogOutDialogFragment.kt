package ru.sogya.projects.smartrevolutionapp.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
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

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle("Log out")
            .setIcon(R.drawable.ic_baseline_logout_24)
            .setMessage("Do you really want to log out")
            .setPositiveButton("Yes") { _, _ ->
                listenner.positiveButtonClicked()
            }
            .setNegativeButton("No") { _, _ ->
                dismiss()
            }
            .create()
    }
}