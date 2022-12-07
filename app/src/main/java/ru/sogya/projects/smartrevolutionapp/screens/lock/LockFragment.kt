package ru.sogya.projects.smartrevolutionapp.screens.lock

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sogya.data.utils.myCallBack
import ru.sogya.projects.smartrevolutionapp.R
import ru.sogya.projects.smartrevolutionapp.databinding.FragmentLockBinding


class LockFragment : Fragment(R.layout.fragment_lock) {
    private lateinit var binding: FragmentLockBinding
    private lateinit var pin: String
    private val vm: LockVM by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLockBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lockerPincodeEditText.setOnEditorActionListener { _, keyCode, _ ->
            var handled = false
            if (
                keyCode == EditorInfo.IME_ACTION_DONE
            ) {
                pin = binding.lockerPincodeEditText.text.toString()
                vm.checkPin(pin, object : myCallBack<Boolean> {
                    override fun data(t: Boolean) {
                        Toast.makeText(context, "Access granted", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_lockFragment_to_homeFragment)
                        handled = true
                    }

                    override fun error() {
                        Toast.makeText(context, "Wrong pin-code", Toast.LENGTH_SHORT).show()
                    }
                })
            }
            return@setOnEditorActionListener handled
        }

    }
}