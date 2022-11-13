package ru.sogya.projects.smartrevolutionapp.screens.authorization

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ru.sogya.projects.smartrevolutionapp.R
import ru.sogya.projects.smartrevolutionapp.databinding.FragmentAuthorizationBinding

class AuthorizationFragment : Fragment(R.layout.fragment_authorization) {
    private lateinit var binding: FragmentAuthorizationBinding
    lateinit var uri: String
    lateinit var password: String
    private val vm: AuthorizationVM by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAuthorizationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonConnect.setOnClickListener {
            uri = binding.editTextUri.text.toString()
            password = binding.editTextToken.text.toString()
            if (uri.endsWith("/")) {
                uri = uri.substring(0, uri.length - 1)
                binding.editTextUri.setText(uri)
                vm.getMessage(uri, password)
            }
            // https://96890a116fb0.sn.mynetname.net/
            // eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiI2NWI0YmU1YjllMzA0YzI3ODEwMDdhNzUyNTM2ZjZjMCIsImlhdCI6MTY2NzMzNDEzNiwiZXhwIjoxOTgyNjk0MTM2fQ.tPyqb82IKVuAUJlH4k0LY_U54ta9GNjGHGPZ5MaSHKs

        }
    }
}