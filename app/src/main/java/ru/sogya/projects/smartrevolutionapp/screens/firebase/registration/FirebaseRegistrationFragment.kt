package ru.sogya.projects.smartrevolutionapp.screens.firebase.registration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.sogya.domain.utils.MyCallBack
import ru.sogya.projects.smartrevolutionapp.R
import ru.sogya.projects.smartrevolutionapp.databinding.FragmentFirebaseRegistrationBinding
import ru.sogya.projects.smartrevolutionapp.dialogs.EmailVerificationDialogFragment

class FirebaseRegistrationFragment : Fragment(R.layout.fragment_firebase_registration) {
    private lateinit var binding: FragmentFirebaseRegistrationBinding
    private val vm: FirebaseRegistrationVM by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFirebaseRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        vm.getErrorLiveData().observe(viewLifecycleOwner) {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
        vm.getLoadingLiveData().observe(viewLifecycleOwner) {
            binding.loadingView.visibility = it
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            binding.buttonSignUp.setOnClickListener {
                val name = editTextName.text.toString()
                val email = editTextEmail.text.toString()
                val password = editTextPassword.text.toString()
                val confirmationPassword = editTextConfirmPassword.text.toString()
                if (email != "" || password != "" || name != "" || confirmationPassword != "") {
                    if (password == confirmationPassword && password.length >= 6)
                        vm.signUpUser(name, email, password, object : MyCallBack<Boolean> {
                            override fun data(t: Boolean) {
                                if (t) {
                                    val dialog = EmailVerificationDialogFragment()
                                    dialog.show(childFragmentManager, dialog.tag)
                                } else
                                    Toast.makeText(
                                        context,
                                        "Something is wrong with your email",
                                        Toast.LENGTH_SHORT
                                    ).show()
                            }

                            override fun error() {
                                Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        })
                    else if (password.length < 6)
                        Toast.makeText(context, "Password to short", Toast.LENGTH_SHORT).show()
                    else
                        Toast.makeText(context, "Passwords don't match", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Some fields are not filled in", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }
}