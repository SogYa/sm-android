package ru.sogya.projects.smartrevolutionapp.dialogs

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.sogya.domain.usecases.network.GetNetworkStateUseCase
import ru.sogya.projects.smartrevolutionapp.R
import ru.sogya.projects.smartrevolutionapp.app.App

class NetworkConnectionDialog : DialogFragment() {
    private val getNetworkStateUseCase = GetNetworkStateUseCase(App.getNetworkStatesRepository())
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (dialog != null && dialog?.window != null)
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)

        return inflater.inflate(R.layout.dialog_ticket_created, container, false)
    }

    override fun onResume() {
        super.onResume()
        getNetworkStateUseCase.invoke().observe(this){
            if(it){
                dismiss()
            }
        }
    }
}