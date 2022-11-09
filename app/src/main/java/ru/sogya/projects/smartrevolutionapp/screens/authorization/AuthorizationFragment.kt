package ru.sogya.projects.smartrevolutionapp.screens.authorization

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.sogya.projects.smartrevolutionapp.R
import ru.sogya.projects.smartrevolutionapp.data.network.NetworkService
import ru.sogya.projects.smartrevolutionapp.data.network.entity.Message
import ru.sogya.projects.smartrevolutionapp.databinding.FragmentAuthorizationBinding

class AuthorizationFragment : Fragment(R.layout.fragment_authorization) {
    private lateinit var binding: FragmentAuthorizationBinding
    lateinit var uri: String
    lateinit var password: String

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
            }
            // https://96890a116fb0.sn.mynetname.net/
            // eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiI2NWI0YmU1YjllMzA0YzI3ODEwMDdhNzUyNTM2ZjZjMCIsImlhdCI6MTY2NzMzNDEzNiwiZXhwIjoxOTgyNjk0MTM2fQ.tPyqb82IKVuAUJlH4k0LY_U54ta9GNjGHGPZ5MaSHKs
            NetworkService.getretrofitService(uri).getApiMessage("Bearer $password")
                .enqueue(object : Callback<Message> {
                    override fun onResponse(
                        call: Call<Message>,
                        response: Response<Message>
                    ) {
                        when (response.code()) {
                            200 -> {
                                Toast.makeText(context, "Access granted", Toast.LENGTH_SHORT).show()
                                findNavController().navigate(R.id.action_authorizationFragment_to_homeFragment,
                                        bundleOf("response" to response.body()?.message.toString()))
                            }
                            201 -> Toast.makeText(context, "Invalid data", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }

                    override fun onFailure(call: Call<Message>, t: Throwable) {
                        Log.d("ERROR", t.message.toString())
                    }

                })
        }
    }
}