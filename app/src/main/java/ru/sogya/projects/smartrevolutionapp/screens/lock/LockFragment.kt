package ru.sogya.projects.smartrevolutionapp.screens.lock

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.sogya.domain.utils.MyCallBack
import dagger.hilt.android.AndroidEntryPoint
import ru.sogya.projects.smartrevolutionapp.R
import ru.sogya.projects.smartrevolutionapp.databinding.FragmentLockBinding

@AndroidEntryPoint
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
                vm.checkPin(pin, object : MyCallBack<Boolean> {
                    override fun data(t: Boolean) {
                        binding.imageViewLocker.setImageResource(R.drawable.ic_baseline_lock_open_24)
                        Toast.makeText(context, "Access granted", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(
                            R.id.action_lockFragment_to_homeFragment, bundleOf(),
                            navOptions {
                                launchSingleTop = true
                                popUpTo(R.id.nav_graph) {
                                    inclusive = true
                                }
                            })
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