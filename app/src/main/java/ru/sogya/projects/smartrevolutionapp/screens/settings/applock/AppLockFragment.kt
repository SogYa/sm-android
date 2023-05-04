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
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentApplockBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            checkLockEnabled(switchTurnBlock.isChecked)
            switchTurnBlock.setOnCheckedChangeListener { _, isChecked ->
                checkLockEnabled(isChecked)
            }
        }


        binding.buttonSave.setOnClickListener {
            pinCode = binding.editTextPincode.text.toString()
            pinCodeVerify = binding.editTextConfirmPincode.text.toString()

            if (binding.switchTurnBlock.isChecked) {
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

    override fun onStart() {
        super.onStart()
        vm.getLockedLiveData().observe(viewLifecycleOwner) {
            binding.switchTurnBlock.isChecked = it
        }
    }

    private fun clearPin() {
        binding.editTextPincode.text = null
        binding.editTextConfirmPincode.text = null
        pinCode = ""
        pinCodeVerify = ""
    }

    private fun checkLockEnabled(isChecked: Boolean) {
        when (isChecked) {
            true -> changeLockEnterElementsVisibility(View.VISIBLE)
            false -> changeLockEnterElementsVisibility(View.INVISIBLE)
        }

    }

    private fun changeLockEnterElementsVisibility(visible: Int) {
        binding.linearLayoutLock.visibility = visible
        binding.buttonSave.visibility = visible
    }
}
