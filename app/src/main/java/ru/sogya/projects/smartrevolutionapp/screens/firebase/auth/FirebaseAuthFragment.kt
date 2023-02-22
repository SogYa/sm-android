package ru.sogya.projects.smartrevolutionapp.screens.firebase.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sogya.domain.utils.MyCallBack
import ru.sogya.projects.smartrevolutionapp.R
import ru.sogya.projects.smartrevolutionapp.databinding.FragmentFirebaseAuthBinding

class FirebaseAuthFragment : Fragment(R.layout.fragment_firebase_auth) {
    private lateinit var binding: FragmentFirebaseAuthBinding
    private val vm: FirebaseAuthVM by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFirebaseAuthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        vm.getLoadingLiveData().observe(viewLifecycleOwner) {
            binding.loadingView.visibility = it
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            buttonLogIn.setOnClickListener {
                val email = editTextEmail.text.toString()
                val password = editTextPassword.text.toString()
                if (email != "" || password != "") {
                    loadingView.visibility = VISIBLE
                    vm.logIn(email, password, object : MyCallBack<Boolean> {
                        override fun data(t: Boolean) {
                            findNavController().navigate(R.id.action_firebaseAuthFragment_to_serversFragment)
                            Toast.makeText(context, "Welcome", Toast.LENGTH_SHORT).show()
                        }

                        override fun error(error: String) {
                            Toast.makeText(context, error, Toast.LENGTH_SHORT)
                                .show()
                        }

                    })
                } else {
                    Toast.makeText(context, "Some fields are not filled in", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            toRegistration.setOnClickListener {
                findNavController().navigate(R.id.action_firebaseAuthFragment_to_firebaseRegistrationFragment)
            }
        }
    }
}