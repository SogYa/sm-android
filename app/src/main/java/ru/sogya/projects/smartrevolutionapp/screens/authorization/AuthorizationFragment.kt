package ru.sogya.projects.smartrevolutionapp.screens.authorization

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
            NetworkService.getretrofitService(uri).getApiMessage("Bearer $password")
                .enqueue(object : Callback<Message> {
                    override fun onResponse(
                        call: Call<Message>,
                        response: Response<Message>
                    ) {
                        when (response.code()) {
                            200 -> {
                                Toast.makeText(context, "Access granted", Toast.LENGTH_SHORT).show()
                                findNavController().navigate(R.id.action_authorizationFragment_to_homeFragment)
                            }
                            201 -> Toast.makeText(context, "Invalid data", Toast.LENGTH_SHORT)
                                .show()
                        }

                    }

                    override fun onFailure(call: Call<Message>, t: Throwable) {
                        Log.d("ERROR", t.message.toString())
                    }

                })

            //Запрос всех сущностей лаборатории

//            NetworkService.getretrofitService(uri).getApiStates("Bearer $password")
//                .enqueue(object : Callback<List<State>> {
//                    override fun onResponse(
//                        call: Call<List<State>>,
//                        response: Response<List<State>>
//                    ) {
//                        Log.d("STATES", response.body().toString())
//                    }
//
//                    override fun onFailure(call: Call<List<State>>, t: Throwable) {
//                        TODO("Not yet implemented")
//                    }
//
//                })
        }
    }
}