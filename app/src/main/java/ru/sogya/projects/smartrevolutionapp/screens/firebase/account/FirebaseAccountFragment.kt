package ru.sogya.projects.smartrevolutionapp.screens.firebase.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sogya.domain.utils.MyCallBack
import ru.sogya.projects.smartrevolutionapp.R
import ru.sogya.projects.smartrevolutionapp.databinding.FragmentFirebaseAccountBinding

class FirebaseAccountFragment : Fragment(R.layout.fragment_firebase_account) {
    private lateinit var binding: FragmentFirebaseAccountBinding
    private val vm: FirebaseAccountVM by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFirebaseAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        vm.getUserLiveData().observe(viewLifecycleOwner) {
            binding.textViewEmail.text = it?.email
            binding.textViewName.text = it?.name
        }
        vm.getErrorLiveData().observe(viewLifecycleOwner){
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            buttonLogOutFirebase.setOnClickListener {
                vm.logOut(object : MyCallBack<Boolean> {
                    override fun data(t: Boolean) {
                        findNavController().navigate(R.id.action_firebaseAccountFragment_to_firebaseAuthFragment)
                        Toast.makeText(context, "Log out complete", Toast.LENGTH_SHORT).show()
                    }

                    override fun error() {}
                })
            }
        }
    }
}