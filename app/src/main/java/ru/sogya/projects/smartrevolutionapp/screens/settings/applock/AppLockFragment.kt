package ru.sogya.projects.smartrevolutionapp.screens.settings.applock

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.sogya.domain.utils.MyCallBack
import ru.sogya.projects.smartrevolutionapp.R
import ru.sogya.projects.smartrevolutionapp.databinding.FragmentApplockBinding


class AppLockFragment : Fragment(R.layout.fragment_applock) {
    private lateinit var binding: FragmentApplockBinding
    private lateinit var pinCode: String
    private lateinit var pinCodeVerify: String
    private val vm = AppLockVM()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentApplockBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (isLocked()) {
            binding.radioGroup.check(R.id.radiobutton_positive)
        } else {
            binding.radioGroup.check(R.id.radioButton_negative)
        }
        binding.radioGroup.setOnCheckedChangeListener { _, p1 ->
            when (p1) {
                R.id.radioButton_negative -> binding.applockLinearlayout.visibility = View.GONE
                R.id.radiobutton_positive -> binding.applockLinearlayout.visibility = View.VISIBLE
            }
        }



        binding.buttonSave.setOnClickListener {
            pinCode = binding.applockEdittextPincode.text.toString()
            pinCodeVerify = binding.applockEdittextPincodeRepeat.text.toString()

            if (isLocked() || binding.radioButtonNegative.isChecked) {
                Toast.makeText(context, "Locker deactivated", Toast.LENGTH_SHORT).show()
                vm.deactivateLocker()
            } else {
                if (pinCode.length < 4 || pinCodeVerify.length < 4) {
                    Toast.makeText(context, "Incorrect length", Toast.LENGTH_SHORT).show()
                } else {
                    vm.setPinCode(pinCode, pinCodeVerify, object : MyCallBack<Boolean> {
                        override fun data(t: Boolean) {
                            Toast.makeText(context, "Locker activated", Toast.LENGTH_SHORT).show()
                        }

                        override fun error() {
                            Toast.makeText(context, "Pin codes must match", Toast.LENGTH_SHORT)
                                .show()
                            clearPin()
                        }

                    })
                }

            }
        }
    }

    private fun isLocked(): Boolean {
        return vm.getLockedInfo()
    }

    private fun clearPin() {
        binding.applockEdittextPincode.text = null
        binding.applockEdittextPincodeRepeat.text = null
        pinCode = ""
        pinCodeVerify = ""
    }
}
